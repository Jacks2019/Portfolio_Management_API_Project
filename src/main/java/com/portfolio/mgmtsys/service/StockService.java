package com.portfolio.mgmtsys.service;

import java.util.List;
import java.util.Map;

import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.model.GetStockHisRequest;
import com.portfolio.mgmtsys.model.GetStockTrendResponse;
import com.portfolio.mgmtsys.model.GetTradesRequest;

public interface StockService {
    List<Stock> getAllStocks(Integer page, Integer pageSize);
    List<Stock> searchStock(Map<String, Object> searchStock);
    Stock getStockByTicker(String ticker);

    List<StockHis> getStockHis(GetStockHisRequest request);
}
