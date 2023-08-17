package com.portfolio.mgmtsys.utils;

/*
 * Name: xiaoyu
 * Date: 2023/8/17
 * Description:
 */

import com.portfolio.mgmtsys.domain.FundHis;
import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.repository.FundHisRepo;
import com.portfolio.mgmtsys.repository.StockHisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class RepoUtil {

    private static StockHisRepo stockHisRepo;
    private static FundHisRepo fundHisRepo;

    @Autowired
    public void setDependency(FundHisRepo fundHisRepo, StockHisRepo stockHisRepo) {
        RepoUtil.fundHisRepo = fundHisRepo;
        RepoUtil.stockHisRepo = stockHisRepo;
    }
    public static List<StockHis> findStockHis(String ticker, Date startTime, Date endTime) {
        StockHis stockHis = new StockHis();
        stockHis.setTicker(ticker);
        Example<StockHis> example = Example.of(stockHis);
        Specification<StockHis> stockHisSpecification = TimeUtil.timeLimit(startTime, endTime, example);
        return stockHisRepo.findAll(stockHisSpecification);
    }

    public static List<FundHis> findFundHis(String code, Date startTime, Date endTime) {
        FundHis fundHis = new FundHis();
        fundHis.setCode(code);
        Example<FundHis> example = Example.of(fundHis);
        Specification<FundHis> stockHisSpecification = TimeUtil.timeLimit(startTime, endTime, example);
        return fundHisRepo.findAll(stockHisSpecification);
    }
}
