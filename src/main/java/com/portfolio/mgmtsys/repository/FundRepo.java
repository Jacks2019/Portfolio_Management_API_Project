package com.portfolio.mgmtsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.mgmtsys.domain.Fund;

public interface FundRepo extends JpaRepository<Fund, String> {
    
}
