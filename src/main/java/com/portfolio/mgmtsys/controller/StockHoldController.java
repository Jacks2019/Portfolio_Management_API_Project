package com.portfolio.mgmtsys.controller;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.Trade;
import com.portfolio.mgmtsys.model.*;
import com.portfolio.mgmtsys.service.StockHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@CrossOrigin
@RequestMapping("/stockhold")
public class StockHoldController {

//    @Qualifier("stockHoldService")
    @Autowired
    StockHoldService service;

    @GetMapping("/getallstockhold/{accountId}")
    public ResponseEntity<Object> getAllStockHold(@PathVariable Integer accountId) {
        LinkedList<MyStockResponse> allStockHold = service.getAllStockHold(accountId);
        if (allStockHold != null && allStockHold.size() !=0 ) {
            return new ResponseEntity<>(allStockHold, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/buystock")
    public ResponseEntity<Object> buyStock(@RequestBody BuyAndSellStockRequest request){
        boolean buystock = service.buyStock(request);
        return buystock ?
                new ResponseEntity<>(true, HttpStatus.ACCEPTED):
                new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/sellstock")
    public ResponseEntity<Object> sellStock(@RequestBody BuyAndSellStockRequest request){
        boolean buystock = service.sellStock(request);
        return buystock ?
                new ResponseEntity<>(true, HttpStatus.ACCEPTED):
                new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/gettrades")
    public ResponseEntity<Object> getTrades(@RequestBody GetTradesRequest request){
        LinkedList<Trade> responses = service.getTrades(request);
        return responses == null ?
                new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED) :
                new ResponseEntity<>(responses, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getallstockholdtrend")
    public ResponseEntity<Object> getAllStockHoldTrend(@RequestBody GetStockTrendRequest request){
        LinkedList<GetStockTrendResponse> responses = service.getAllStockHoldTrend(request);
        return responses == null ?
                new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED) :
                new ResponseEntity<>(responses, HttpStatus.ACCEPTED);
    }

}
