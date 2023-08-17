package com.portfolio.mgmtsys.service;

/*
 * Name: xiaoyu
 * Date: 2023/8/16
 * Description:
 */

import com.portfolio.mgmtsys.domain.FundHold;
import com.portfolio.mgmtsys.domain.FundTrade;
import com.portfolio.mgmtsys.model.BuyAndSellFundRequest;
import com.portfolio.mgmtsys.model.GetTradesRequest;

import java.util.LinkedList;
import java.util.List;

public interface FundHoldService {
    List<FundHold> getAllFundHold(Integer accountId);

    boolean buyFund(BuyAndSellFundRequest request);

    boolean sellFund(BuyAndSellFundRequest request);

    LinkedList<FundTrade> getTrades(GetTradesRequest request);
}
