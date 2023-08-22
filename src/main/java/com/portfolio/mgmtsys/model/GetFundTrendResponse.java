package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */


import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GetFundTrendResponse extends Response{
    // [{股票名称，股票代码，[股票价格]}]

    private String code;
    private List<Double> prices;

    private List<Date> dates;

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

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
    @Override
    public String toString() {
        return "GetFundTrendResponse{" +
                "code='" + code + '\'' +
                ", prices=" + prices +
                ", dates=" + dates +
                '}';
    }

    public void addDates(Date[] time) {
        Date startTime = time[0];
        Date endTime = time[1];
        dates = new LinkedList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);

        while (calendar.getTime().before(endTime) || calendar.getTime().equals(endTime)) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
    }
}
