package com.portfolio.mgmtsys.service;

import java.util.List;

import com.portfolio.mgmtsys.domain.Fund;

public interface FundService {
    List<Fund> getAllFunds();
    Fund getFundByCode(String code);
}
