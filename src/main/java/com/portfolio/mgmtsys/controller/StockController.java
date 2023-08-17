package com.portfolio.mgmtsys.controller;

import java.util.List;
import java.util.Map;

import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.service.StockService;

@RestController
@CrossOrigin
@RequestMapping("/stock")
public class StockController {
    
    @Autowired
    private StockService service;

    @GetMapping("/getallstocks")
    public ResponseEntity<Object> getAllStocks(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize){
        return new ResponseEntity<Object>(service.getAllStocks(page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/searchstock")
    public ResponseEntity<Object> searchStock(@RequestParam(required = false) Map<String, Object> searchMap){
        List<Stock> stocks = service.searchStock(searchMap);
        if(stocks.size()>0){
            return new ResponseEntity<Object>(stocks, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("No stock found.", HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/getstockbyticker/{ticker}")
    public ResponseEntity<Object> getStockByTicker(@PathVariable String ticker){
        Stock stock = service.getStockByTicker(ticker);
        if(stock!=null){
            return new ResponseEntity<Object>(stock, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("No stock with this ticker is found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getstockhis")
    public ResponseEntity<Object> getStockHis(@RequestBody GetStockHisRequest request){
        List<StockHis> responses = service.getStockHis(request);
        if (responses == null || responses.size() == 0){
            return new ResponseEntity<>("No stock with this ticker is found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
