package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */


import java.util.List;

public class GetFundTrendResponse extends Response{
    // [{股票名称，股票代码，[股票价格]}]

    private String code;
    private List<Double> prices;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "GetFundTrendResponse{" +
                "code='" + code + '\'' +
                ", prices=" + prices +
                '}';
    }
}
