package com.portfolio.mgmtsys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
    
    @Autowired
    private StockService service;

    @GetMapping("/getAllStocks")
    public ResponseEntity<Object> getAllStocks(){
        return new ResponseEntity<Object>(service.getAllStocks(), HttpStatus.FOUND);
    }

    @GetMapping("/searchStock")
    public ResponseEntity<Object> searchStock(@RequestParam(required = false) Map<String, Object> searchMap){
        List<Stock> stocks = service.searchStock(searchMap);
        if(stocks.size()>0){
            return new ResponseEntity<Object>(stocks, HttpStatus.FOUND);
        }
        return new ResponseEntity<Object>("No stock found.", HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/getStockByTicker/{ticker}")
    public ResponseEntity<Object> getStockByTicker(@PathVariable String ticker){
        Stock stock = service.getStockByTicker(ticker);
        if(stock!=null){
            return new ResponseEntity<Object>(stock, HttpStatus.FOUND);
        }
        return new ResponseEntity<Object>("No stock with this ticker is found.", HttpStatus.NOT_FOUND);
    }
    
}
