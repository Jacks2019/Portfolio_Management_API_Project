package com.portfolio.mgmtsys.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.repository.StockRepo;
import com.portfolio.mgmtsys.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepo repo;

    public List<Stock> getAllStocks(Integer page, Integer pageSize) {
        PageRequest pagination = PageRequest.of(page==null?0:page-1, pageSize==null?50:pageSize);
        return repo.findAll(pagination).toList();
    }

    @Override
    public List<Stock> searchStock(Map<String, Object> searchMap) {
        Stock searchStock = new Stock();
        Class<Stock> stockClazz = Stock.class;
        // Set the query conditions with Reflection
        for(String key:searchMap.keySet()){
            try {
                Field field = stockClazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(searchStock, searchMap.get(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Object page = searchMap.get("page");
        Object pageSize = searchMap.get("pageSize");
        PageRequest pagination = PageRequest.of(page==null?0:(Integer)page-1, pageSize==null?50:(Integer)pageSize);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("ticker", GenericPropertyMatchers.contains())
            .withMatcher("name", GenericPropertyMatchers.contains());
        Example<Stock> searchExample = Example.of(searchStock, matcher);
        return repo.findAll(searchExample, pagination).toList();
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        Stock tickerStock = new Stock();
        tickerStock.setTicker(ticker);
        Example<Stock> tickerExample = Example.of(tickerStock);
        Optional<Stock> stockOptional = repo.findOne(tickerExample);
        if(stockOptional.isPresent()){
            return stockOptional.get();
        }
        return null;
    }
    
}
