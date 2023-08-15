package com.portfolio.mgmtsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.mgmtsys.domain.Fund;
import com.portfolio.mgmtsys.service.FundService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/fund")
public class FundController {
    
    @Autowired
    private FundService service;

    @GetMapping("/getallfunds")
    public ResponseEntity<Object> getAllFunds() {
        return new ResponseEntity<Object>(service.getAllFunds(), HttpStatus.FOUND);
    }

    @GetMapping("/getfundbycode")
    public ResponseEntity<Object> getFundByCode(@RequestParam String code){
        Fund fund = service.getFundByCode(code);
        if(fund==null){
            return new ResponseEntity<Object>("No fund with this code found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(fund, HttpStatus.FOUND);
    }
    
}
