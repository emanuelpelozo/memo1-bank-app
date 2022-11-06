package com.aninfo.service;

import com.aninfo.controller.TransactionResponse;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.aninfo.model.TransactionType.DEPOSIT;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;

    public TransactionResponse createTransaction(final Transaction transaction) {
        final Account account = applyMovement(
                transaction.getType(),
                transaction.getCbu(),
                transaction.getAmount()
        );

        final Transaction savedTransaction= transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .id(savedTransaction.getId())
                .amount(savedTransaction.getAmount())
                .cbu(savedTransaction.getCbu())
                .type(transaction.getType())
                .accountBalance(account.getBalance())
                .build();
    }

    private Account applyMovement(final TransactionType type,
                                  final Long cbu,
                                  final Double amount) {

        if (isNull(type)) {
            throwInvalidExceptionType();
        }

        return type == DEPOSIT
                ? accountService.deposit(cbu, amount)
                : accountService.withdraw(cbu, amount);
    }

    private void throwInvalidExceptionType() {
        throw new InvalidTransactionTypeException(
                format("Transaction types should be one of: [%s]",
                        stream(TransactionType.values())
                                .map(Objects::toString)
                                .collect(joining(","))
                        )
        );
    }
}
