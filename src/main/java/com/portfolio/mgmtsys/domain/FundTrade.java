package com.portfolio.mgmtsys.domain;

import java.util.Date;

import com.portfolio.mgmtsys.enumeration.FundTradeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fund_trade")
@Data
public class FundTrade {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "code")
    private String code;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private FundTradeType type;

    @Column(name = "time")
    private Date time;

    @Column(name = "price")
    private Double price;

    @Column(name = "amount")
    private Integer amount;

}
