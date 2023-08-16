package com.portfolio.mgmtsys.service;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.Trade;
import com.portfolio.mgmtsys.model.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;


public interface StockHoldService {

    LinkedList<MyStockResponse> getAllStockHold(Integer accountId);

    boolean buyStock(BuyStockRequest request);

    boolean sellStock(SellStockRequest request);

    LinkedList<Trade> getTrades(GetTradesRequest request);

    LinkedList<GetStockTrendResponse> getAllStockHoldTrend(GetStockTrendRequest request);
}
