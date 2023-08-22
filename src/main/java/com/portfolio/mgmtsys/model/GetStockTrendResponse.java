package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */


import java.util.*;

public class GetStockTrendResponse  extends Response{
    // [{股票名称，股票代码，[股票价格]}]
    private String name;
    private String ticker;
    private List<Double> prices;

    private List<Date> dates = new LinkedList<>();


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

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "GetStockTrendResponse{" +
                "name='" + name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", prices=" +  prices.toString() +
                ", dates=" + dates +
                '}';
    }

    public void addDates(Date[] time) {
        Date startTime = time[0];
        Date endTime = time[1];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);

        while (calendar.getTime().before(endTime) || calendar.getTime().equals(endTime)) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
    }
}
