package com.portfolio.mgmtsys.repository;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepo extends JpaRepository<Trade,Integer>, JpaSpecificationExecutor<Trade> {

}
