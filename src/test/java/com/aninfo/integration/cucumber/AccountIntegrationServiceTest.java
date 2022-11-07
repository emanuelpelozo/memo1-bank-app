package com.aninfo.integration.cucumber;

import com.aninfo.Memo1BankApp;
import com.aninfo.controller.TransactionResponse;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static com.aninfo.model.TransactionType.DEPOSIT;
import static com.aninfo.model.TransactionType.WITHDRAW;

@ContextConfiguration(classes = Memo1BankApp.class)
@WebAppConfiguration
public class AccountIntegrationServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    Account createAccount(Double balance) {
        return accountService.createAccount(new Account(balance));
    }

    TransactionResponse withdraw(Long cbu, Double amount) {
        return createTransaction(cbu, amount, WITHDRAW);
    }

    TransactionResponse deposit(Long cbu, Double amount) {
        return createTransaction(cbu, amount, DEPOSIT);
    }

    List<Transaction> getTransactionsFor(Long cbu) {
        return transactionService.findTransactionsByCbu(cbu);
    }

    void deleteTransactionById(Long id) {
        transactionService.deleteById(id);
    }

    private TransactionResponse createTransaction(Long cbu, Double amount, TransactionType type) {
        return transactionService.createTransaction(new Transaction(cbu, amount, type));
    }

}
