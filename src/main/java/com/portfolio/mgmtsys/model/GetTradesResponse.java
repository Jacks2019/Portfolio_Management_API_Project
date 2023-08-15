package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */

import com.portfolio.mgmtsys.domain.Trade;
import com.portfolio.mgmtsys.enumeration.TradeType;

public class GetTradesResponse {
    // [{操作类型，股票名字，股票代码，操作时价格，买入卖出数量}]
    private TradeType tradeType;

    private String stockName;

    private String stockTicker;

    private Double price;

    private Integer amount;

    public GetTradesResponse(Trade trade) {
        this.tradeType = trade.getType();
        this.amount = trade.getAmount();
        
    }


}
