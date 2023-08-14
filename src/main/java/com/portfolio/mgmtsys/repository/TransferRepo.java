package com.portfolio.mgmtsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.mgmtsys.domain.Transfer;

public interface TransferRepo extends JpaRepository<Transfer, Integer> {
    
}
