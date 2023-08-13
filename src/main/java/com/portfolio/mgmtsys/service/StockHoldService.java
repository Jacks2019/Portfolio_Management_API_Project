package com.portfolio.mgmtsys.service;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.model.MyStock;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public interface StockHoldService {

    LinkedList<MyStock> getAllStockHold(Integer accountId);
}
