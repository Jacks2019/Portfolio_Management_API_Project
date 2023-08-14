package com.portfolio.mgmtsys.service;

import java.util.List;
import java.util.Map;

import com.portfolio.mgmtsys.domain.Stock;

public interface StockService {
    List<Stock> getAllStocks(Integer page, Integer pageSize);
    List<Stock> searchStock(Map<String, Object> searchStock);
    Stock getStockByTicker(String ticker);
}
