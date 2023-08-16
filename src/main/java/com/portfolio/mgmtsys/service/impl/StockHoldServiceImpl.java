package com.portfolio.mgmtsys.service.impl;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.*;
import com.portfolio.mgmtsys.model.*;
import com.portfolio.mgmtsys.repository.*;
import com.portfolio.mgmtsys.service.StockHoldService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
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

    private Stock findStockByTicker(String ticker){
        Stock stock = new Stock();
        stock.setTicker(ticker);
        Example<Stock> example = Example.of(stock);
        Optional<Stock> optionalStock = stockRepo.findOne(example);
        return optionalStock.orElse(null);
    }

    private StockHold findStockHoldByTickerAndAccount(String ticker, Integer accountId){
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
        Specification<Trade> tradeSpecification = timeLimit(startTime, endTime, example);
        return tradeRepo.findAll(tradeSpecification);
    }

    private List<StockHis> findStockHis(String ticker, Date startTime, Date endTime) {
        StockHis stockHis = new StockHis();
        stockHis.setTicker(ticker);
        Example<StockHis> example = Example.of(stockHis);
        Specification<StockHis> stockHisSpecification = timeLimit(startTime, endTime, example);
        return stockHisRepo.findAll(stockHisSpecification);
    }

    private <T> Specification<T> timeLimit(Date startTime, Date endTime, Example<T> example) {
        return (Specification<T>) (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.greaterThan(root.get("time"), startTime));
            predicates.add(builder.lessThan(root.get("time"),endTime));
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
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
    public boolean buyStock(BuyStockRequest request) {
        if (request == null
                || request.getAccountId() == null
                || request.getAmount() == null
                || request.getTicker() == null
                || request.getAmount() <= 0

        ){
            return false;
        }
        // 1. 获取账户资金
        Assets asset = assetsRepo.getReferenceById(request.getAccountId());

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
            stockHold.setAmount(request.getAmount());
            stockHold.setCallPrice(stock.getCurrentPrice());
            stockHold.setTicker(request.getTicker());
            stockHold.setAmount(0);
        }
        System.out.println("ticker: "+stockHold.getTicker());
        // 5. 修改StockHold表和Assets表
        stockHold.setAmount(stockHold.getAmount()+request.getAmount());
        asset.setBalance(now);
        asset.setStockAssets(0.0);
        asset.setTotalAssets(10000.0);

        Assets save = assetsRepo.save(asset);
//        StockHold save1 = stockHoldRepo.save(stockHold);
        System.out.println("balance "+save.getBalance());
//        System.out.println("amount "+save1.getAmount());
        assetsRepo.flush();
//        stockHoldRepo.flush();
        return true;
    }

    /**
     *
     * @param request {登陆ID，股票代码，销售数目}
     * @return 售卖是否成功
     */
    @Override
    public boolean sellStock(SellStockRequest request) {
        if (request == null
                || request.getAccountId() == null
                || request.getAmount() == null
                || request.getTicker() == null
                || request.getAmount() <= 0
        ){
            return false;
        }
        // 1. 获取账户资金
        Assets asset = assetsRepo.getReferenceById(request.getAccountId());
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

        // 5. 修改StockHold表和Assets表
        stockHold.setAmount(stockHold.getAmount() - request.getAmount());
        Double now = asset.getBalance() + currentPrice * request.getAmount();
        asset.setBalance(now);
        assetsRepo.save(asset);
        stockHoldRepo.save(stockHold);

       return false;
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
        
        // 2. 获取时间限制，默认七天内
        // 定义日期范围
        // 获取当前时间
        Date[] time = extractedTime(request);
        if (time[0].getTime() > time[1].getTime()){
            return null;
        }

        LinkedList<Trade> responses = new LinkedList<>();
        for (StockHold myStock : myStocks) {
            // 股票的具体信息
            Stock stock = findStockByTicker(myStock.getTicker());
            List<Trade> trades = findTradesByTickerAndAccount(stock.getTicker(), request.getAccountId(),request.getStartTime(),request.getEndTime());
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
    public LinkedList<GetStockTrendResponse> getAllStockHoldTrend(GetStockTrendRequest request) {
        // 1. 根据登陆Id查询所持有的所有股票
        ArrayList<StockHold> myStocks = stockHoldRepo.findAllByAccountId(request.getAccountId());
        if (myStocks == null || myStocks.size() == 0){
            return null;
        }
        LinkedList<MyStockResponse> list = new LinkedList<>();
        // 2. 获取时间限制，默认七天内
        // 定义日期范围
        // 获取当前时间
        Date[] time = extractedTime(request);
        if (time[0].getTime() > time[1].getTime()){
            return null;
        }

        // 3. 获取时间范围内股票信息
        LinkedList<GetStockTrendResponse> responses = new LinkedList<>();
        for (StockHold myStock : myStocks) {
            List<StockHis> stockHis = findStockHis(myStock.getTicker(), request.getStartTime(), request.getEndTime());
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



    private Date[] extractedTime(TimeRequest request) {
        Date[] time = new Date[2];
        time[0] = new Date();
        // 创建 Calendar 对象并设置为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time[0]);
        // 将日期往前推7天
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        // 获取七天前的时间
        time[1] = calendar.getTime();

        time[0] = request.getStartTime() == null ? time[0]: request.getStartTime();// 设置开始时间
        time[1] = request.getEndTime() == null ? time[1] : request.getEndTime();// 设置结束时间
        return time;
    }


}
