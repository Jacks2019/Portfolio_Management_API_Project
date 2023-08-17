package com.portfolio.mgmtsys.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fund_his")
@Data
public class FundHis{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date time;

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