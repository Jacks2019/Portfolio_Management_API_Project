package com.portfolio.mgmtsys.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.repository.StockRepo;
import com.portfolio.mgmtsys.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepo repo;

    public List<Stock> getAllStocks() {
        return repo.findAll();
    }

    @Override
    public List<Stock> searchStock() {
        throw new UnsupportedOperationException("Unimplemented method 'searchStock'");
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        Stock tickerStock = new Stock();
        Example<Stock> tickerExample = Example.of(tickerStock);
        Optional<Stock> stockOptional = repo.findOne(tickerExample);
        if(stockOptional.isPresent()){
            return stockOptional.get();
        }
        return null;
    }
    
}
