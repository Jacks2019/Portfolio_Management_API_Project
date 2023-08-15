package com.portfolio.mgmtsys.controller;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.model.BuyStockRequest;
import com.portfolio.mgmtsys.model.MyStockResponse;
import com.portfolio.mgmtsys.model.SellStockRequest;
import com.portfolio.mgmtsys.service.StockHoldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/stockhold")
public class StockHoldController {

    StockHoldService service;

    @GetMapping("/getallstockhold")
    public ResponseEntity<Object> getAllStockHold(@RequestBody Integer accountId){
        LinkedList<MyStockResponse> allStockHold = service.getAllStockHold(accountId);
        if (allStockHold != null){
            return new ResponseEntity<>(allStockHold, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<Object>("No relevant information found.", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/buystock")
    public ResponseEntity<Object> buyStock(@RequestBody BuyStockRequest request){
        boolean buystock = service.buyStock(request);
        return buystock ?
                new ResponseEntity<>(true, HttpStatus.ACCEPTED):
                new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/sellstock")
    public ResponseEntity<Object> sellStock(@RequestBody SellStockRequest request){
        boolean buystock = service.sellStock(request);
        return buystock ?
                new ResponseEntity<>(true, HttpStatus.ACCEPTED):
                new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }
}
