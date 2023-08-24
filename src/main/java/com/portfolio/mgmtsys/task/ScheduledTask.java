package com.portfolio.mgmtsys.task;

/*
 * Name: xiaoyu
 * Date: 2023/8/18
 * Description:
 */

import com.portfolio.mgmtsys.domain.*;
import com.portfolio.mgmtsys.model.MyStockResponse;
import com.portfolio.mgmtsys.repository.*;
import com.portfolio.mgmtsys.service.FundHoldService;
import com.portfolio.mgmtsys.service.impl.StockHoldServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    AssetsRepo assetsRepo;

    @Autowired
    StockHoldRepo stockHoldRepo;

    @Autowired
    FundHoldRepo fundHoldRepo;

    @Autowired
    FundRepo fundRepo;

    @Autowired
    StockRepo stockRepo;


    /**
     * 定时更新Assert
     */
//    @Scheduled(cron = "0 45 12 * * *") // 每天九点半执行
    @Scheduled(cron = "0 30 9 * * *") // 每天九点半执行
    public void scheduleUpdateAsserts() {
        // 定时任务的逻辑代码
        System.out.println("定时任务执行中...");
        // 1. 获取所有Asset表数据
        List<Assets> assetList = assetsRepo.findAll();

        // 2. 根据id查找 持有的stock和fund
        for (Assets assets : assetList) {
            Double stockAss = 0.0;
            Double fundAss = 0.0;

            List<StockHold> allStockHold = stockHoldRepo.findAll(Example.of(new StockHold(assets.getId())));
            for (StockHold stockHold : allStockHold) {
                // 通过ticker查找stock的价格
                Stock stock = stockRepo.findById(stockHold.getTicker()).orElse(null);
                Double currentPrice = stock.getCurrentPrice();
                stockAss += currentPrice * stockHold.getAmount();
            }
            List<FundHold> allFundHold = fundHoldRepo.findAll(Example.of(new FundHold(assets.getId())));
            for (FundHold fundHold : allFundHold) {
                Fund fund = fundRepo.findById(fundHold.getCode()).orElse(null);
                Double unitNet = fund.getUnitNet();
                fundAss += unitNet * fundHold.getAmount();
            }

            assets.setStockAssets(stockAss);
            assets.setFundAssets(fundAss);
            assets.setTotalAssets(stockAss + fundAss + assets.getBalance());
            assetsRepo.save(assets);
        }
    }
}