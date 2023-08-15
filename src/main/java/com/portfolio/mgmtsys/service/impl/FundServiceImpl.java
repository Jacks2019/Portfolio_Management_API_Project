package com.portfolio.mgmtsys.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Fund getFundByCode(String code) {
        Optional<Fund> fundOptional = repo.findById(code);
        if(fundOptional.isPresent()){
            return fundOptional.get();
        }
        return null;
    
    }
    
}
