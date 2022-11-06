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

//    Account withdraw(Account account, Double sum) {
//        return accountService.withdraw(account.getCbu(), sum);
//    }

    TransactionResponse withdraw(Long cbu, Double amount) {
        return createTransaction(cbu, amount, WITHDRAW);
    }

//    Account deposit(Account account, Double sum) {
//        return accountService.deposit(account.getCbu(), sum);
//    }

    TransactionResponse deposit(Long cbu, Double amount) {
        return createTransaction(cbu, amount, DEPOSIT);
    }

    private TransactionResponse createTransaction(Long cbu, Double amount, TransactionType type) {
        return transactionService.createTransaction(new Transaction(cbu, amount, type));
    }

}
