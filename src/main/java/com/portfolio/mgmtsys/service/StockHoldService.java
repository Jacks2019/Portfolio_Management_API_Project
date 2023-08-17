package com.portfolio.mgmtsys.service;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.Trade;
import com.portfolio.mgmtsys.model.*;

import java.util.LinkedList;


public interface StockHoldService {

    LinkedList<MyStockResponse> getAllStockHold(Integer accountId);

    boolean buyStock(BuyAndSellStockRequest request);

    boolean sellStock(BuyAndSellStockRequest request);

    LinkedList<Trade> getTrades(GetTradesRequest request);

    LinkedList<GetStockTrendResponse> getAllStockHoldTrend(GetTrendRequest request);
}
