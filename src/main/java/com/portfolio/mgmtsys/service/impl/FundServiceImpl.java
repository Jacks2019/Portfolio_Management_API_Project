package com.portfolio.mgmtsys.service.impl;

import java.util.List;

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
        throw new UnsupportedOperationException("Unimplemented method 'getAllFunds'");
    }

    @Override
    public Fund getFundByCode(String code) {
        throw new UnsupportedOperationException("Unimplemented method 'getFundByCode'");
    }
    
}
