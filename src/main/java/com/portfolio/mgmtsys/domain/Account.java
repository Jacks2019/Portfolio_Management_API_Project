package com.portfolio.mgmtsys.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

// @Entity
@Data
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String accountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "password")
    private String password;
}
