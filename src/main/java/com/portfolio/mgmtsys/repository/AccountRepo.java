package com.portfolio.mgmtsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.mgmtsys.domain.Account;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    
}
