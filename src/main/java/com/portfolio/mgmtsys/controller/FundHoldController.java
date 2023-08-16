package com.portfolio.mgmtsys.controller;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.FundHold;
import com.portfolio.mgmtsys.domain.Trade;
import com.portfolio.mgmtsys.model.*;
import com.portfolio.mgmtsys.service.FundHoldService;
import com.portfolio.mgmtsys.service.StockHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/fundhold")
public class FundHoldController {

    @Autowired
    FundHoldService service;

    @GetMapping("/getallfundhold/{accountId}")
    public ResponseEntity<Object> getAllStockHold(@PathVariable Integer accountId) {
        List<FundHold> allFundHold = service.getAllFundHold(accountId);
        if (allFundHold != null && allFundHold.size() != 0) {
            return new ResponseEntity<>(allFundHold, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }


}
