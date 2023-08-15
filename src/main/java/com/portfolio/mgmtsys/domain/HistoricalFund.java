package com.portfolio.mgmtsys.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "historical_fund", indexes = {@Index(columnList = "code")})
@Data
public class HistoricalFund {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;

    @Column(name = "unit_net")
    private Double unitNet;

    @Column(name = "acc_net")
    private Double accNet;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "subscription")
    private String subscription;

    @Column(name = "redemption")
    private String redemption;
}