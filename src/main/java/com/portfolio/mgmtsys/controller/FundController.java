package com.portfolio.mgmtsys.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.portfolio.mgmtsys.domain.FundHis;
import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.model.GetFundHisRequest;
import com.portfolio.mgmtsys.model.GetStockHisRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.mgmtsys.domain.Fund;
import com.portfolio.mgmtsys.service.FundService;


@RestController
@CrossOrigin
@RequestMapping("/fund")
public class FundController {
    
    @Autowired
    private FundService service;

    @GetMapping("/getallfunds")
    public ResponseEntity<Object> getAllFunds(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<Object>(service.getAllFunds(page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/searchfund")
    public ResponseEntity<Object> searchFund(@RequestParam(required = false) Map<String, Object> query) {
        List<Fund> funds = service.searchFund(query);
        if(funds.size()<=0){
            return new ResponseEntity<Object>("No fund found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(funds, HttpStatus.OK);
    }

    @GetMapping("/getfundbycode")
    public ResponseEntity<Object> getFundByCode(@RequestParam String code){
        Fund fund = service.getFundByCode(code);
        if(fund==null){
            return new ResponseEntity<Object>("No fund with this code found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(fund, HttpStatus.OK);
    }

    @GetMapping("/getfundhis")
    public ResponseEntity<Object> getFundHis(@RequestParam("code") String code,
                                             @RequestParam(value ="startTime", required = false) String startTime,
                                             @RequestParam(value = "endTime", required = false) String endTime){
        GetFundHisRequest request = new GetFundHisRequest(code, startTime, endTime);
        List<FundHis> responses = service.getFundHis(request);
        if (responses == null || responses.size() == 0){
            return new ResponseEntity<>("No stock with this ticker is found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    
}
