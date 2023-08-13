package com.portfolio.mgmtsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.mgmtsys.domain.Stock;

public interface StockRepo extends JpaRepository<Stock, Integer> {
    
}
