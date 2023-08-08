package com.portfolio.mgmtsys.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "stock_hold")
@Data
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
}
