package com.portfolio.mgmtsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.mgmtsys.domain.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    
}
