package com.portfolio.mgmtsys.service.impl;

/*
 * Name: xiaoyu
 * Date: 2023/8/16
 * Description:
 */

import com.portfolio.mgmtsys.domain.*;
import com.portfolio.mgmtsys.enumeration.FundTradeType;
import com.portfolio.mgmtsys.enumeration.TradeType;
import com.portfolio.mgmtsys.model.*;
import com.portfolio.mgmtsys.repository.*;
import com.portfolio.mgmtsys.service.FundHoldService;
import com.portfolio.mgmtsys.utils.RepoUtil;
import com.portfolio.mgmtsys.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class FundHoldServiceImpl implements FundHoldService {
    @Autowired
    FundHoldRepo fundHoldRepo;

    @Autowired
    AssetsRepo assetsRepo;

    @Autowired
    FundRepo fundRepo;

    @Autowired
    FundTradeRepo fundTradeRepo;

    @Autowired
    FundHisRepo fundHisRepo;


    /**
     * 查询所有持有基金：/fundhold/getallfubdhold
     * 请求方式：get
     * @param accountId 登陆ID
     * @return [fundhold]
     */
    @Override
    public List<FundHold> getAllFundHold(Integer accountId) {
        // 1. 通过accountID查找所有FundHold
        List<FundHold> fundHolds = fundHoldRepo.findAllByAccountId(accountId);
        return fundHolds;
    }

    /**
     * 购买基金：/fundhold/buyfund
     * 请求方式：post
     * Response：{true/false}
     * @param request 登陆ID，基金代码，购买数目
     * @return {true/false}
     */
    @Override
    public boolean buyFund(BuyAndSellFundRequest request) {
        System.out.println(request);
        // 1. 通过登陆ID查找资产信息
        Assets asset = assetsRepo.findById(request.getAccountId()).orElse(null);
        if(asset == null){
            return false;
        }

        // 2. 通过基金代码查找基金信息
        Example<Fund> example = Example.of(new Fund(request.getCode()));
        Fund fund = fundRepo.findOne(example).orElse(null);
        if (fund == null){
            return false;
        }

        // 3. 新建/获取fundhold，通过code和accountId
        Example<FundHold> fundHoldExample = Example.of(new FundHold(request.getAccountId(), request.getCode()));
        FundHold fundHold = fundHoldRepo.findOne(fundHoldExample).orElse(null);
        if (fundHold == null){
            fundHold = new FundHold(request.getAccountId(), request.getCode());
            fundHold.setAmount(request.getAmount());
            fundHold.setSubscriptionPrice(fund.getUnitNet());
        }

        // 4. 判断能否购买
        Double now = asset.getBalance() - request.getAmount() * fund.getUnitNet();
        System.out.println("now = " + now);
        if (now < 0){
            return false;
        }

        // 5. 修改fundTrade、fundhold和assets表
        FundTrade fundTrade = new FundTrade(request.getAccountId(), request.getCode(),
                FundTradeType.SUBSCRIBE, TimeUtil.getNowTime(),fund.getUnitNet(), request.getAmount());

        fundHold.setAmount(fundHold.getAmount() + request.getAmount());
        asset.setBalance(now);
        asset.setFundAssets(asset.getFundAssets() + request.getAmount() * fund.getUnitNet());
        asset.setTotalAssets(asset.getStockAssets() + asset.getFundAssets() + asset.getBalance());

        // 6. 存入数据库
        fundTradeRepo.save(fundTrade);
        fundHoldRepo.save(fundHold);
        assetsRepo.save(asset);

        return true;
    }

    @Override
    public boolean sellFund(BuyAndSellFundRequest request) {
        if (request == null
                || request.getAccountId() == null
                || request.getAmount() == null
                || request.getCode() == null
                || request.getAmount() <= 0
        ){
            return false;
        }
        // 1. 获取账户资金
        Assets asset = assetsRepo.findById(request.getAccountId()).orElse(null);
        if (asset == null){
            return false;
        }

        // 2. 获取基金价格
        Fund fund = fundRepo.findById(request.getCode()).orElse(null);

        if(fund == null){
            return false;
        }
        Double currentPrice = fund.getUnitNet();

        // 3. 查找fundHold表
        FundHold fundHold = fundHoldRepo.findOne(Example.of(new FundHold(request.getAccountId(), request.getCode()))).orElse(null);
        if (fundHold == null){
            return false;
        }

        // 4. 判断是否可以售卖
        if (fundHold.getAmount() < request.getAmount()){
            return false;
        }

        // 5. 修改tradde表
        Date time = TimeUtil.getNowTime();
        FundTrade trade = new FundTrade(request.getAccountId(), request.getCode(),
                FundTradeType.REDEEM, time, currentPrice, request.getAmount());
        // 6. 修改StockHold表和Assets表
        fundHold.setAmount(fundHold.getAmount() - request.getAmount());
        Double now = asset.getBalance() + currentPrice * request.getAmount();
        asset.setBalance(now);
        asset.setFundAssets(asset.getFundAssets() - request.getAmount() * fund.getUnitNet());
        asset.setTotalAssets(asset.getStockAssets() + asset.getFundAssets() + asset.getBalance());

        // 7. 存入数据库
        fundTradeRepo.save(trade);
        assetsRepo.save(asset);
        fundHoldRepo.save(fundHold);
        return true;
    }

    /**
     * 查询基金交易记录：/fundhold/gettrades
     * @param request 登陆ID，交易时间段（默认近七天）
     * @return [FundTrade：{操作类型，基金名字，基金代码，操作时价格，买入卖出数量，操作时间}]
     */
    @Override
    public LinkedList<FundTrade> getTrades(GetTradesRequest request) {

        // 1. 获取fundhold
        Integer accountId = request.getAccountId();
        // 我的所有基金
        ArrayList<FundHold> myFunds = fundHoldRepo.findAllByAccountId(accountId);
        if (myFunds == null || myFunds.size() == 0){
            return null;
        }

        // 2. 获取时间限制，默认七天内
        // 定义日期范围
        // 获取当前时间
        Date[] time = TimeUtil.extractedTime(request);
//        System.out.println(request);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        System.out.println(dateFormat.format(time[0]));
//        System.out.println(dateFormat.format(time[1]));
        if (time[0].after(time[1])){
            return null;
        }

        LinkedList<FundTrade> responses = new LinkedList<>();
        for (FundHold myFund : myFunds) {
            Fund fund = fundRepo.findById(myFund.getCode()).orElse(null);
            if (fund == null){
                continue;
            }
            List<FundTrade> trades = findTradesByCodeAndAccount(fund.getCode(), request.getAccountId(),time[0], time[1]);
            responses.addAll(trades);
        }

        return responses;
    }

    /**
     * 查询持有基金价格变化：/fundhold/getallfundkholdtrend
     * @param request 登陆ID，查询时间段（默认近七天）
     * @return [{基金名称，基金代码，[基金价格]}]
     */
    @Override
    public LinkedList<GetFundTrendResponse> getAllFundHoldTrend(GetTrendRequest request) {
        // 1. 根据登陆Id查询所持有的所有基金
        if (request == null){
            return null;
        }

        List<FundHold> myFunds = fundHoldRepo.findAll(Example.of(new FundHold(request.getAccountId())));
        if (myFunds == null || myFunds.size() == 0){
            return null;
        }

        // 2. 获取时间限制，默认七天内
        // 定义日期范围
        // 获取当前时间
        Date[] time = TimeUtil.extractedTime(request);
        if (time[0].after(time[1])){
            return null;
        }


        // 3. 获取时间范围内基金信息
        LinkedList<GetFundTrendResponse> responses = new LinkedList<>();
        for (FundHold myFund : myFunds) {
            List<FundHis> fundHis = findFundHis(myFund.getCode(),time[0], time[1]);
            if (fundHis.size() == 0){
                continue;
            }
            GetFundTrendResponse response = new GetFundTrendResponse();
            List<Double> prices = new LinkedList<>();
            response.setCode(myFund.getCode());

            for (FundHis stockHis0 : fundHis) {
                prices.add(stockHis0.getUnitNet());
            }
            response.setPrices(prices);
            responses.add(response);
        }

        return responses;
    }



    private List<FundTrade> findTradesByCodeAndAccount(String code, Integer accountId, Date startTime, Date endTime) {
        FundTrade trade = new FundTrade();
        trade.setAccountId(accountId);
        trade.setCode(code);
        Example<FundTrade> example = Example.of(trade);
        Specification<FundTrade> tradeSpecification = TimeUtil.timeLimit(startTime, endTime, example);
        return fundTradeRepo.findAll(tradeSpecification);
    }

    private List<FundHis> findFundHis(String code, Date startTime, Date endTime) {
        FundHis fundHis = new FundHis();
        fundHis.setCode(code);
        Example<FundHis> example = Example.of(fundHis);
        Specification<FundHis> stockHisSpecification = TimeUtil.timeLimit(startTime, endTime, example);
        return fundHisRepo.findAll(stockHisSpecification);
    }
}
