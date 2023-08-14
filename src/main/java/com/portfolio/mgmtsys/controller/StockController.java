package com.portfolio.mgmtsys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
    
    @Autowired
    private StockService service;

    @GetMapping("/getAllStocks")
    public List<Stock> getAllStocks(){
        return service.getAllStocks();
    }
}
