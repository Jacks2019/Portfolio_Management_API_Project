package com.portfolio.mgmtsys.service;

import java.util.List;

import com.portfolio.mgmtsys.domain.Stock;

public interface StockService {
    List<Stock> getAllStocks();
    List<Stock> searchStock();
    Stock getStockByTicker(String ticker);
}
