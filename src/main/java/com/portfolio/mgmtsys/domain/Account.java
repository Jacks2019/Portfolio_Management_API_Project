package com.portfolio.mgmtsys.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// @Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;
}
