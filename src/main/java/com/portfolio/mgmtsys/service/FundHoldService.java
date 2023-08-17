package com.portfolio.mgmtsys.service;

/*
 * Name: xiaoyu
 * Date: 2023/8/16
 * Description:
 */

import com.portfolio.mgmtsys.domain.FundHold;
import com.portfolio.mgmtsys.model.BuyAndSellFundRequest;

import java.util.List;

public interface FundHoldService {
    List<FundHold> getAllFundHold(Integer accountId);

    boolean buyFund(BuyAndSellFundRequest request);

    boolean sellFund(BuyAndSellFundRequest request);
}
