package com.portfolio.mgmtsys.service.impl;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.domain.StockHold;
import com.portfolio.mgmtsys.model.MyStock;
import com.portfolio.mgmtsys.repository.StockHoldRepo;
import com.portfolio.mgmtsys.repository.StockRepo;
import com.portfolio.mgmtsys.service.StockHoldService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedList;

public class StockHoldServiceImpl implements StockHoldService {

    @Autowired
    StockRepo stockRepo;

    @Autowired
    StockHoldRepo stockHoldRepo;

    @Override
    public LinkedList<MyStock> getAllStockHold(Integer accountId) {
        if (accountId == null){
            return null;
        }
        ArrayList<StockHold> myStocks = stockHoldRepo.findAllByAccountId(accountId);
        if (myStocks == null || myStocks.size() == 0){
            return null;
        }
        LinkedList<MyStock> list = new LinkedList<>();
        for (StockHold myStock : myStocks) {
            String ticker = myStock.getTicker();
            Integer amount = myStock.getAmount();
            ArrayList<Stock> stocks = stockRepo.findAllByTicker(ticker);
            if (stocks == null || stocks.size() != 1){
                continue;
            }
            Stock stock = stocks.get(0);
            String name = stock.getName();
            Double currentPrice = stock.getCurrentPrice();
            list.add(new MyStock(name,ticker,amount,currentPrice));
        }

        return list;
    }
}
