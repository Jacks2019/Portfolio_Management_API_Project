package com.portfolio.mgmtsys.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.stereotype.Service;

import com.portfolio.mgmtsys.domain.Fund;
import com.portfolio.mgmtsys.repository.FundRepo;
import com.portfolio.mgmtsys.service.FundService;

@Service
public class FundServiceImpl implements FundService {

    @Autowired
    private FundRepo repo;

    @Override
    public List<Fund> getAllFunds() {
        return repo.findAll();
    }

    public List<Fund> searchFund(Map<String, Object> query){
        Fund fund = new Fund();
        fund.setCode((String)query.get("code"));
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("code", GenericPropertyMatchers.contains());
        Example<Fund> fundExample = Example.of(fund, matcher);
        List<Fund> funds = repo.findAll(fundExample);
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
