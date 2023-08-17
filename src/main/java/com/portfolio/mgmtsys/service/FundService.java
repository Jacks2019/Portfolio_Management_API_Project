package com.portfolio.mgmtsys.service;

import java.util.List;
import java.util.Map;

import com.portfolio.mgmtsys.domain.Fund;
import com.portfolio.mgmtsys.domain.FundHis;
import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.model.GetFundHisRequest;

public interface FundService {
    List<Fund> getAllFunds(Integer page, Integer pageSize);
    List<Fund> searchFund(Map<String, Object> query);
    Fund getFundByCode(String code);

    List<FundHis> getFundHis(GetFundHisRequest request);
}
