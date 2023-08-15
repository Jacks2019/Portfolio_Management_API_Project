package com.portfolio.mgmtsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.mgmtsys.domain.FundHold;

public interface FundHoldRepo extends JpaRepository<FundHold, Integer> {
    
}
