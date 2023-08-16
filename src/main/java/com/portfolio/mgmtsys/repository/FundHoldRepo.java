package com.portfolio.mgmtsys.repository;

import com.portfolio.mgmtsys.domain.StockHold;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.mgmtsys.domain.FundHold;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//@Repository
public interface FundHoldRepo extends JpaRepository<FundHold, Integer> {

    @Query("SELECT e FROM FundHold  e WHERE e.accountId = :accountId")
    ArrayList<FundHold> findAllByAccountId(Integer accountId);
}
