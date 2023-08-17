package com.portfolio.mgmtsys.repository;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.FundHis;
import com.portfolio.mgmtsys.domain.StockHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FundHisRepo extends JpaRepository<FundHis,Integer>, JpaSpecificationExecutor<FundHis> {

}
