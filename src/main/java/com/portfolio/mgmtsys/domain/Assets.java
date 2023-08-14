package com.portfolio.mgmtsys.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "assets")
@Data
public class Assets {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "total_assets")
    private Double totalAssets;

    @Column(name = "stock_assets")
    private Double stockAssets;

    @Column(name = "balance")
    private Double balance;
}
