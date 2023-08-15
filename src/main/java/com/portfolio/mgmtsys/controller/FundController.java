package com.portfolio.mgmtsys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Fund> getAllFunds() {
        return service.getAllFunds();
    }
    
}
