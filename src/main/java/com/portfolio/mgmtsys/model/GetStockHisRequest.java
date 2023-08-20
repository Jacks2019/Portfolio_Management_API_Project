package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/17
 * Description:
 */

import java.util.Date;

public class GetStockHisRequest extends TimeRequest{
    private String ticker;

    public GetStockHisRequest(String ticker, Date startTime, Date endTime) {
        super(startTime, endTime);
        this.ticker = ticker;
    }
    public GetStockHisRequest(){

    }

    public GetStockHisRequest(String ticker, String startTime, String endTime) {
        super(startTime, endTime);
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
