package com.portfolio.mgmtsys.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
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
    public List<Stock> searchStock(Map<String, Object> searchMap) {
        Stock searchStock = new Stock();
        Class<Stock> stockClazz = Stock.class;
        // Set the query condition with Reflection
        for(String key:searchMap.keySet()){
            try {
                Field field = stockClazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(searchStock, searchMap.get(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("ticker", GenericPropertyMatchers.contains())
            .withMatcher("name", GenericPropertyMatchers.contains());
        Example<Stock> searchExample = Example.of(searchStock, matcher);
        return repo.findAll(searchExample);
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
