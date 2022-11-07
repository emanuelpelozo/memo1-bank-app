package com.aninfo.controller;

import com.aninfo.model.TransactionType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionResponse {
    private Long id;
    private TransactionType type;
    private Double amount;
    private Long cbu;
    private Double accountBalance;
}
