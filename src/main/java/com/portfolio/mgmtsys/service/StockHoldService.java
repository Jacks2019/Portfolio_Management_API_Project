package com.portfolio.mgmtsys.service;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.model.BuyStockRequest;
import com.portfolio.mgmtsys.model.MyStockResponse;
import com.portfolio.mgmtsys.model.SellStockRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public interface StockHoldService {

    LinkedList<MyStockResponse> getAllStockHold(Integer accountId);

    boolean buyStock(BuyStockRequest request);

    boolean sellStock(SellStockRequest request);
}
