package com.portfolio.mgmtsys.service.impl;

import java.lang.reflect.Field;
import java.util.*;

import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.model.GetStockHisRequest;
import com.portfolio.mgmtsys.repository.StockHisRepo;
import com.portfolio.mgmtsys.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.repository.StockRepo;
import com.portfolio.mgmtsys.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepo repo;

    @Autowired
    private StockHisRepo stockHisRepo;

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

    /**
     * stock/getstockhis
     * @param request ticker，time
     * @return 时间范围内股票价格
     */
    @Override
    public List<StockHis> getStockHis(GetStockHisRequest request) {
        // 1. 判断request
        if (request == null || request.getTicker() == null){
            return null;
        }
        // 2. 获取时间限制，默认七天内
        // 定义日期范围
        // 获取当前时间
        Date[] time = TimeUtil.extractedTime(request);
        if (time[0].after(time[1])){
            return null;
        }
        List<StockHis> stockHis = findStockHis(request.getTicker(), time[0], time[1]);

        return stockHis;
    }

    private List<StockHis> findStockHis(String ticker, Date startTime, Date endTime) {
        StockHis stockHis = new StockHis();
        stockHis.setTicker(ticker);
        Example<StockHis> example = Example.of(stockHis);
        Specification<StockHis> stockHisSpecification = TimeUtil.timeLimit(startTime, endTime, example);
        return stockHisRepo.findAll(stockHisSpecification);
    }
}
