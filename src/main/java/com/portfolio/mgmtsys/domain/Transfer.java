package com.portfolio.mgmtsys.domain;

import java.util.Date;

import com.portfolio.mgmtsys.enumeration.TransferType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "transfer", indexes = {@Index(columnList = "account_id")})
@Data
public class Transfer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private TransferType type;

    @Column(name = "time")
    private Date time;

    @Column(name = "amount")
    private Integer amount;
}
