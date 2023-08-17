package com.portfolio.mgmtsys.domain;


import jakarta.persistence.*;

import lombok.Data;

@Table(name = "stock_hold")
@Data
@Entity
public class StockHold {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id")
    private int accountId;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "call_price")
    private Double callPrice;

    @Column(name = "amount")
    private Integer amount;

    public StockHold(int accountId) {
        this.accountId = accountId;
    }
    public StockHold(){

    }
}
