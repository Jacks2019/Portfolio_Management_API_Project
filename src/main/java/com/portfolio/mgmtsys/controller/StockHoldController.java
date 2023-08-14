package com.portfolio.mgmtsys.controller;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.model.MyStock;
import com.portfolio.mgmtsys.service.StockHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
@RequestMapping("/stock")
public class StockHoldController {

    @Autowired
    StockHoldService service;

    @PostMapping("/getallstockhold")
    public ResponseEntity<Object> getAllStockHold(@RequestBody Integer accountId){
        LinkedList<MyStock> allStockHold = service.getAllStockHold(accountId);
        if (allStockHold != null){
            return new ResponseEntity<>(allStockHold, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<Object>("No relevant information found.", HttpStatus.UNAUTHORIZED);
    }
}
