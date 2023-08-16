package com.portfolio.mgmtsys.service.impl;

/*
 * Name: xiaoyu
 * Date: 2023/8/16
 * Description:
 */

import com.portfolio.mgmtsys.domain.FundHold;
import com.portfolio.mgmtsys.repository.FundHoldRepo;
import com.portfolio.mgmtsys.service.FundHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class FundHoldServiceImpl implements FundHoldService {
    @Autowired
    FundHoldRepo fundHoldRepo;

    /**
     * 查询所有持有基金：/fundhold/getallfubdhold
     * 请求方式：get
     * @param accountId 登陆ID
     * @return [fundhold]
     */
    @Override
    public List<FundHold> getAllFundHold(Integer accountId) {
        // 1. 通过accountID查找所有FundHold
        List<FundHold> fundHolds = fundHoldRepo.findAllByAccountId(accountId);
        return fundHolds;
    }
}
