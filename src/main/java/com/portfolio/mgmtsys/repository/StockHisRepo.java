package com.portfolio.mgmtsys.repository;

/*
 * Name: xiaoyu
 * Date: 2023/8/13
 * Description:
 */

import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.domain.Trade;
import com.portfolio.mgmtsys.utils.TimeUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StockHisRepo extends JpaRepository<StockHis,Integer>, JpaSpecificationExecutor<StockHis> {

}
