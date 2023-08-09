package com.portfolio.mgmtsys.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "assets", indexes = {@Index(columnList = "account_id")})
@Data
public class Assets {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "total_assets")
    private Double totalAssets;

    @Column(name = "stock_assets")
    private Double stockAssets;

    @Column(name = "balance")
    private Double balance;
}
