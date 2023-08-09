package com.portfolio.mgmtsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.repository.StockRepo;
import com.portfolio.mgmtsys.service.StockService;

public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepo repo;

    public List<Stock> getAllStocks() {
        return repo.findAll();
    }
    
}
