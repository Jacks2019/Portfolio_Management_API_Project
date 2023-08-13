package com.portfolio.mgmtsys.domain;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "account")
@Data
@Entity
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;
}
