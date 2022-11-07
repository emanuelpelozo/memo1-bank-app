package com.aninfo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cbu;

    private Double amount;

    private TransactionType type;

    public Transaction(final Long cbu,
                       final Double amount,
                       final TransactionType type) {
        this.cbu = cbu;
        this.amount = amount;
        this.type = type;
    }
}
