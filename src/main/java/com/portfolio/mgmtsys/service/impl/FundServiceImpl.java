package com.portfolio.mgmtsys.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.portfolio.mgmtsys.domain.Fund;
import com.portfolio.mgmtsys.repository.FundRepo;
import com.portfolio.mgmtsys.service.FundService;

@Service
public class FundServiceImpl implements FundService {

    @Autowired
    private FundRepo repo;

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
    
}
