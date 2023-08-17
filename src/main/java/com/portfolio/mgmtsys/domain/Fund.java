package com.portfolio.mgmtsys.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fund")
@Data
public class Fund{

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "time")
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

    public Fund(){

    }

    public Fund(String code) {
        this.code = code;
    }
}