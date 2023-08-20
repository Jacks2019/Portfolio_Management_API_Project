package com.portfolio.mgmtsys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.portfolio.mgmtsys.domain.FundHis;
import com.portfolio.mgmtsys.model.GetFundHisRequest;
import com.portfolio.mgmtsys.repository.FundHisRepo;
import com.portfolio.mgmtsys.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.portfolio.mgmtsys.domain.Fund;
import com.portfolio.mgmtsys.repository.FundRepo;
import com.portfolio.mgmtsys.service.FundService;

@Service
public class FundServiceImpl implements FundService {

    @Autowired
    private FundRepo repo;

    @Autowired
    private FundHisRepo fundHisRepo;

    @Override
    public List<Fund> getAllFunds(Integer page, Integer pageSize) {
        PageRequest pagination = PageRequest.of(page==null?0:page-1, pageSize==null?50:pageSize);
        return repo.findAll(pagination).toList();
    }

    @Override
    public List<Fund> searchFund(Map<String, Object> query){
        Fund fund = new Fund();
        fund.setCode((String)query.get("code"));
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("code", GenericPropertyMatchers.contains());
        Example<Fund> fundExample = Example.of(fund, matcher);
        Object page = query.get("page");
        Object pageSize = query.get("pageSize");
        PageRequest pagination = PageRequest.of(page==null?0:(Integer)page-1, pageSize==null?50:(Integer)pageSize);
        List<Fund> funds = repo.findAll(fundExample, pagination).toList();
        return funds;
    }

    @Override
    public Fund getFundByCode(String code) {
        Optional<Fund> fundOptional = repo.findById(code);
        if(fundOptional.isPresent()){
            return fundOptional.get();
        }
        return null;
    
    }

    @Override
    public List<FundHis> getFundHis(GetFundHisRequest request) {
        // 1. 判断request
        if (request == null || request.getCode() == null){
            return null;
        }
        // 2. 获取时间限制，默认七天内
        // 定义日期范围
        // 获取当前时间
        Date[] time = TimeUtil.extractedTime(request);
        if (time[0].after(time[1])){
            return null;
        }
        List<FundHis> fundHis = findFundHis(request.getCode(), time[0], time[1]);

        return fundHis;
    }

    private List<FundHis> findFundHis(String code, Date startTime, Date endTime) {
        FundHis fundHis = new FundHis();
        fundHis.setCode(code);
        Example<FundHis> example = Example.of(fundHis);
        Specification<FundHis> stockHisSpecification = TimeUtil.timeLimit(startTime, endTime, example);
        return fundHisRepo.findAll(stockHisSpecification);
    }

}
