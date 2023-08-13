package com.portfolio.mgmtsys.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stock_hold", indexes = {@Index(columnList = "account_id")})
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
