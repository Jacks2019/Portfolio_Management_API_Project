package com.portfolio.mgmtsys.controller;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.FundHold;
import com.portfolio.mgmtsys.domain.FundTrade;
import com.portfolio.mgmtsys.model.*;
import com.portfolio.mgmtsys.service.FundHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/fundhold")
public class FundHoldController {

    @Autowired
    FundHoldService service;

    @GetMapping("/getallfundhold/{accountId}")
    public ResponseEntity<Object> getAllStockHold(@PathVariable Integer accountId) {
        List<FundHold> allFundHold = service.getAllFundHold(accountId);
        if (allFundHold != null && allFundHold.size() != 0) {
            return new ResponseEntity<>(allFundHold, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/buyfund")
    public ResponseEntity<Object> buyFund(@RequestBody BuyAndSellFundRequest request){
        boolean buystock = service.buyFund(request);
        return buystock ?
                new ResponseEntity<>(true, HttpStatus.OK):
                new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/sellfund")
    public ResponseEntity<Object> sellFund(@RequestBody BuyAndSellFundRequest request){
        boolean buystock = service.sellFund(request);
        return buystock ?
                new ResponseEntity<>(true, HttpStatus.OK):
                new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/gettrades")
    public ResponseEntity<Object> sellFund(@RequestParam(value = "accountId") Integer accountId,
                                           @RequestParam(value ="startTime", required = false) String startTime,
                                           @RequestParam(value = "endTime", required = false) String endTime){
        GetTradesRequest request = new GetTradesRequest(accountId, startTime, endTime);
        LinkedList<FundTrade> response= service.getTrades(request);
        return response == null?
                new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED):
                new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getallfundholdtrend")
    public ResponseEntity<Object> getAllStockHoldTrend(@RequestParam("accountId") Integer accountId,
                                                       @RequestParam(value ="startTime", required = false) String startTime,
                                                       @RequestParam(value = "endTime", required = false) String endTime){
        GetTrendRequest request = new GetTrendRequest(accountId, startTime, endTime);
        LinkedList<GetFundTrendResponse> responses = service.getAllFundHoldTrend(request);
        return responses == null ?
                new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED) :
                new ResponseEntity<>(responses, HttpStatus.OK);
    }


}
