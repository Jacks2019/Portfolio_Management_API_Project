package com.portfolio.mgmtsys.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "stock", indexes = {@Index(columnList = "ticker")})
@Data
public class Stock {
    @Id
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "open_price")
    private Double openPrice;

    @Column(name = "high_price")
    private Double highPrice;

    @Column(name = "low_price")
    private Double lowPrice;

    @Column(name = "close_price")
    private Double closePrice;

    @Column(name = "vol")
    private Double vol;
}
