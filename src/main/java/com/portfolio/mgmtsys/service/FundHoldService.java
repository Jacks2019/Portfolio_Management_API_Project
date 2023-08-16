package com.portfolio.mgmtsys.service;

/*
 * Name: xiaoyu
 * Date: 2023/8/16
 * Description:
 */

import com.portfolio.mgmtsys.domain.FundHold;

import java.util.LinkedList;
import java.util.List;

public interface FundHoldService {
    List<FundHold> getAllFundHold(Integer accountId);
}
