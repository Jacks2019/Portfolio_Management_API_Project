package com.portfolio.mgmtsys.service;

import java.util.List;

import com.portfolio.mgmtsys.domain.Fund;

public interface FundService {
    List<Fund> getAllFunds(Integer page, Integer pageSize);
    Fund getFundByCode(String code);
}
