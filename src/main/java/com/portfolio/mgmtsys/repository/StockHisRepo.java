package com.portfolio.mgmtsys.repository;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StockHisRepo extends JpaRepository<StockHis,Integer>, JpaSpecificationExecutor<StockHis> {

}
