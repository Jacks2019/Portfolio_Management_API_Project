package com.portfolio.mgmtsys.service.impl;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.*;
import com.portfolio.mgmtsys.enumeration.TradeType;
import com.portfolio.mgmtsys.model.*;
import com.portfolio.mgmtsys.repository.*;
import com.portfolio.mgmtsys.service.StockHoldService;
import com.portfolio.mgmtsys.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StockHoldServiceImpl implements StockHoldService {

    @Autowired
    StockRepo stockRepo;
    @Autowired
    StockHoldRepo stockHoldRepo;
    @Autowired
    AssetsRepo assetsRepo;
    @Autowired
    TradeRepo tradeRepo;

    @Autowired
    StockHisRepo stockHisRepo;

    public Stock findStockByTicker(String ticker){
        Stock stock = new Stock();
        stock.setTicker(ticker);
        Example<Stock> example = Example.of(stock);
        Optional<Stock> optionalStock = stockRepo.findOne(example);
        return optionalStock.orElse(null);
    }

    public StockHold findStockHoldByTickerAndAccount(String ticker, Integer accountId){
        StockHold stockHold = new StockHold();
        stockHold.setAccountId(accountId);
        stockHold.setTicker(ticker);
        Example<StockHold> example = Example.of(stockHold);
        Optional<StockHold> optionalStock = stockHoldRepo.findOne(example);
        return optionalStock.orElse(null);
    }

    private List<Trade> findTradesByTickerAndAccount(String ticker, Integer accountId, Date startTime, Date endTime) {
        Trade trade = new Trade();
        trade.setAccountId(accountId);
        trade.setTicker(ticker);
        Example<Trade> example = Example.of(trade);
        Specification<Trade> tradeSpecification = TimeUtil.timeLimit(startTime, endTime, example);
        return tradeRepo.findAll(tradeSpecification);
    }





    /**
     * 通过登陆ID查找持有股票信息，涉及Stock表和StockHold表
     * @param accountId 账户ID
     * @return [{股票名，股票代码，持股数目，股票价格}]
     */
    @Override
    public LinkedList<MyStockResponse> getAllStockHold(Integer accountId) {
        if (accountId == null){
            return null;
        }
        // 我的所有持股
        ArrayList<StockHold> myStocks = stockHoldRepo.findAllByAccountId(accountId);
        if (myStocks == null || myStocks.size() == 0){
            return null;
        }
        LinkedList<MyStockResponse> list = new LinkedList<>();
        for (StockHold myStock : myStocks) {
            String ticker = myStock.getTicker();
            Integer amount = myStock.getAmount();

            // 通过股票代码查找股票信息
            Stock stock = findStockByTicker(ticker);
            // 如果该股票在Stock中找不到，则忽略（正常情况下不会出现）
            if (stock == null){
                continue;
            }
            String name = stock.getName();
            Double currentPrice = stock.getCurrentPrice();
            list.add(new MyStockResponse(name,ticker,amount,currentPrice));
        }

        return list;
    }

    /**
     *
     * @param request {登陆ID，股票代码，购买数目}
     * @return 购买是否成功
     */
    @Override
    public boolean buyStock(BuyAndSellStockRequest request) {
        if (request == null
                || request.getAccountId() == null
                || request.getAmount() == null
                || request.getTicker() == null
                || request.getAmount() <= 0

        ){
            return false;
        }
        // 1. 获取账户资金
        Assets asset = assetsRepo.findById(request.getAccountId()).orElse(null);
        if (asset == null){
            return false;
        }

        // 2. 获取股票价格
        Stock stock = findStockByTicker(request.getTicker());
        if(stock == null){
            return false;
        }
        Double currentPrice = stock.getCurrentPrice();

        // 3. 判断是否完成购买

        Double now = asset.getBalance() - currentPrice * request.getAmount();
        if (now < 0){
            return false;
        }

        // 4. 查找stockHold表
        StockHold stockHold = findStockHoldByTickerAndAccount(request.getTicker(), request.getAccountId());

        if (stockHold == null){
            // 新建stokHold
            stockHold = new StockHold();
            stockHold.setAccountId(request.getAccountId());
            stockHold.setCallPrice(stock.getCurrentPrice());
            stockHold.setTicker(request.getTicker());
            stockHold.setAmount(0);
        }
        System.out.println("ticker: "+stockHold.getTicker());
        // 5. 修改tradde表、StockHold表和Assets表
        Date time = TimeUtil.getNowTime();

        Trade trade = new Trade(request.getAccountId(), request.getTicker(),TradeType.BUY, time, currentPrice, request.getAmount());
        stockHold.setAmount(stockHold.getAmount()+request.getAmount());
        asset.setBalance(now);
        asset.setStockAssets(asset.getStockAssets() + currentPrice * request.getAmount());
        asset.setTotalAssets(asset.getStockAssets() + asset.getFundAssets() + asset.getBalance());

        // 6. 存入数据库

        tradeRepo.save(trade);
        assetsRepo.save(asset);
        stockHoldRepo.save(stockHold);

        return true;
    }

    /**
     *
     * @param request {登陆ID，股票代码，销售数目}
     * @return 售卖是否成功
     */
    @Override
    public boolean sellStock(BuyAndSellStockRequest request) {
        if (request == null
                || request.getAccountId() == null
                || request.getAmount() == null
                || request.getTicker() == null
                || request.getAmount() <= 0
        ){
            return false;
        }
        // 1. 获取账户资金
        Assets asset = assetsRepo.findById(request.getAccountId()).orElse(null);
        if (asset == null){
            return false;
        }

        // 2. 获取股票价格
        Stock stock = findStockByTicker(request.getTicker());
        if(stock == null){
            return false;
        }
        Double currentPrice = stock.getCurrentPrice();

        // 3. 查找stockHold表
        StockHold stockHold = findStockHoldByTickerAndAccount(request.getTicker(), request.getAccountId());
        if (stockHold == null){
            return false;
        }

        // 4. 判断是否可以售卖
        if (stockHold.getAmount() < request.getAmount()){
            return false;
        }

        // 5. 修改trade表 修改StockHold表和Assets表
        Date time = TimeUtil.getNowTime();
        Trade trade = new Trade(request.getAccountId(), request.getTicker(),TradeType.SELL, time, currentPrice, request.getAmount());
        stockHold.setAmount(stockHold.getAmount() - request.getAmount());

        Double now = asset.getBalance() + currentPrice * request.getAmount();
        asset.setBalance(now);
        asset.setStockAssets(asset.getStockAssets() - currentPrice * request.getAmount());
        asset.setTotalAssets(asset.getStockAssets() + asset.getFundAssets() + asset.getBalance());

        // 6. 存入数据库
        tradeRepo.save(trade);
        assetsRepo.save(asset);
        stockHoldRepo.save(stockHold);
        return true;
    }

    /**
     *
     * @param request {登陆ID，交易时间段（默认近七天）}
     * @return [Trade：{操作类型，股票名字，股票代码，操作时价格，买入卖出数量，操作时间}]
     */
    @Override
    public LinkedList<Trade> getTrades(GetTradesRequest request) {

        // 1. 获取stockhold
        Integer accountId = request.getAccountId();
        // 我的所有持股
        ArrayList<StockHold> myStocks = stockHoldRepo.findAllByAccountId(accountId);
        if (myStocks == null || myStocks.size() == 0){
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

        LinkedList<Trade> responses = new LinkedList<>();

        for (StockHold myStock : myStocks) {
            // 股票的具体信息
            Stock stock = stockRepo.findById(myStock.getTicker()).orElse(null);
//                    findStockByTicker(myStock.getTicker());
            if (stock == null){
                continue;
            }
            List<Trade> trades = findTradesByTickerAndAccount(stock.getTicker(), request.getAccountId(),time[0],time[1]);
            responses.addAll(trades);
        }

        return responses;
    }

    /**
     * 查询持股价格变化
     * @param request {登陆ID，查询时间段（默认近七天）}
     * @return [{股票名称，股票代码，[股票价格]}]
     */
    @Override
    public LinkedList<GetStockTrendResponse> getAllStockHoldTrend(GetTrendRequest request) {
        // 1. 根据登陆Id查询所持有的所有股票
        if (request == null){
            return null;
        }

        List<StockHold> myStocks = stockHoldRepo.findAll(Example.of(new StockHold(request.getAccountId())));
        if (myStocks == null || myStocks.size() == 0){
            return null;
        }

        // 2. 获取时间限制，默认七天内
        // 定义日期范围
        // 获取当前时间
        Date[] time = TimeUtil.extractedTime(request);
        if (time[0].after(time[1])){
            return null;
        }
//        for (StockHold myStock : myStocks) {
//            List<StockHis> all = stockHisRepo.findAll(Example.of(new StockHis(myStock.getTicker())));
//            for (StockHis stockHis : all) {
//                System.out.println(stockHis);
//            }
//            System.out.println("=====");
//        }

        // 3. 获取时间范围内股票信息
        LinkedList<GetStockTrendResponse> responses = new LinkedList<>();
        for (StockHold myStock : myStocks) {
            List<StockHis> stockHis = findStockHis(myStock.getTicker(), time[0], time[1]);
            if (stockHis.size() == 0){
                // 没有记录
                continue;
            }
            GetStockTrendResponse response = new GetStockTrendResponse();
            List<Double> prices = new LinkedList<>();
            response.setName(myStock.getTicker());
            response.setTicker(stockHis.get(0).getTicker());
            for (StockHis stockHis0 : stockHis) {
                prices.add(stockHis0.getCurrentPrice());
            }
            response.setPrices(prices);
            responses.add(response);
        }
        return responses;
    }

    public List<StockHis> findStockHis(String ticker, Date startTime, Date endTime) {
        StockHis stockHis = new StockHis();
        stockHis.setTicker(ticker);
        Example<StockHis> example = Example.of(stockHis);
        Specification<StockHis> stockHisSpecification = TimeUtil.timeLimit(startTime, endTime, example);
        return stockHisRepo.findAll(stockHisSpecification);
    }






}
