package com.portfolio.mgmtsys.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fund_hold")
@Data
public class FundHold {
    
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "code")
    private String code;

    @Column(name = "subscription_price")
    private Double subscriptionPrice;

    @Column(name = "amount")
    private Integer amount;
}
