package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */


import java.util.List;

public class GetStockTrendResponse  extends Response{
    // [{股票名称，股票代码，[股票价格]}]
    private String name;
    private String ticker;
    private List<Double> prices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }
}
