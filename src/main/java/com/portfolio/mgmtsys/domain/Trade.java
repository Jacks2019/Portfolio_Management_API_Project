package com.portfolio.mgmtsys.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "trade")
@Data
public class Trade {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "type")
    private int type;

    @Column(name = "time")
    private Date time;

    @Column(name = "price")
    private Double price;

    @Column(name = "amount")
    private Integer amount;
    
}
