package com.aninfo.integration.cucumber;

import com.aninfo.controller.TransactionResponse;
import com.aninfo.exceptions.AccountDoesNotExistException;
import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.AfterEach;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.aninfo.model.TransactionType.DEPOSIT;
import static com.aninfo.model.TransactionType.WITHDRAW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class AccountOperationsTest extends AccountIntegrationServiceTest {

    private Account account;
    private TransactionResponse transactionResponse;

    private List<Transaction> transactionList;
    private InsufficientFundsException ife;
    private DepositNegativeSumException dnse;
    private AccountDoesNotExistException adnee;

    @Before
    public void setup() {
        System.out.println("Before any test execution");
    }

    @Given("^Account with a balance of (\\d+)$")
    public void account_with_a_balance_of(int balance)  {
        account = createAccount((double) balance);
    }

    @When("^Trying to withdraw (\\d+)$")
    public void trying_to_withdraw(int sum) {
        try {
            transactionResponse = withdraw(account.getCbu(), (double) sum);
        } catch (InsufficientFundsException ife) {
            this.ife = ife;
        }
    }

    @When("^Trying to deposit (.*)$")
    public void trying_to_deposit(int sum) {
        try {
            transactionResponse = deposit(account.getCbu(), (double) sum);
        } catch (DepositNegativeSumException dnse) {
            this.dnse = dnse;
        }
    }

    @Then("^Account balance should be (\\d+)$")
    public void account_balance_should_be(int balance) {
        assertEquals(Double.valueOf(balance), transactionResponse.getAccountBalance());
    }

    @Then("^Operation should be denied due to insufficient funds$")
    public void operation_should_be_denied_due_to_insufficient_funds() {
        assertNotNull(ife);
    }

    @Then("^Operation should be denied due to negative sum$")
    public void operation_should_be_denied_due_to_negative_sum() {
        assertNotNull(dnse);
    }

    @And("^Account balance should remain (\\d+)$")
    public void account_balance_should_remain(int balance) {
        assertEquals(Double.valueOf(balance), account.getBalance());
    }

    @Given("^Account with a deposit transaction of (\\d+)$")
    public void accountWithADepositTransactionOf(int sum) {
        account = createAccount(100.0);
        transactionResponse = deposit(account.getCbu(), (double) sum);
    }

    @When("^Trying to retrieve transactions for account$")
    public void tryingToRetrieveTransactionsForAccount() {
        try {
            transactionList = getTransactionsFor(account.getCbu());
        } catch (AccountDoesNotExistException ex) {
            adnee = ex;
        }
    }

    @Then("^Obtain transaction of (\\d+) for account$")
    public void obtainTransactionOfForAccount(int sum) {
        assertEquals(1, transactionList.size());
        final Transaction transaction = transactionList.get(0);

        assertEquals(Double.valueOf(sum), transaction.getAmount());
        assertEquals(account.getCbu(), transaction.getCbu());
    }

    @Then("^Obtain (\\s+) transaction of (\\d+) for account")
    public void obtainDepositTransactionOfForAccount(String transactionType, int sum) {
        assertEquals(1, transactionList.size());
        final Transaction transaction = transactionList.get(0);

        assertEquals(TransactionType.from(transactionType), transaction.getType());
        assertEquals(Double.valueOf(sum), transaction.getAmount());
        assertEquals(account.getCbu(), transaction.getCbu());
    }

    @Then("^Obtain deposit transaction of (\\d+) for account$")
    public void obtainDepositTransactionOfForAccount(int sum) {
        assertEquals(1, transactionList.size());

        assertTransaction(1, sum, DEPOSIT);
    }

    @Given("^Account with a withdraw transaction of (\\d+)$")
    public void accountWithAWithdrawTransactionOf(int sum) {
        account = createAccount(100.0);
        withdraw(account.getCbu(), (double) sum);
    }

    @Then("^Obtain withdraw transaction of (\\d+) for account$")
    public void obtainWithdrawTransactionOfForAccount(int sum) {
        assertEquals(1, transactionList.size());
        assertTransaction(1, sum, WITHDRAW);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Given("^Non existent account$")
    public void nonExistentAccount() {
        // Non existent account
        account = new Account(1L, 0.0);
    }

    @Then("^Operation should fail due to non existent account$")
    public void operationShouldFailDueToNonExistentAccount() {
        assertNotNull(adnee);
    }

    @And("^Same account with a deposit transaction of (\\d+)$")
    public void sameAccountWithADepositTransactionOf(int sum) {
        deposit(account.getCbu(), (double) sum);
    }

    @Then("^Obtain withdraw transaction of (\\d+) for account in (\\d+) place$")
    public void obtainWithdrawTransactionOfForAccountInPlace(int sum, int transactionNumber) {
        assertTransaction(transactionNumber, sum, WITHDRAW);
    }

    @And("^Obtain deposit transaction of (\\d+) for account in (\\d+) place$")
    public void obtainDepositTransactionOfForAccountInPlace(int sum, int transactionNumber) {
        assertTransaction(transactionNumber, sum, DEPOSIT);
    }

    @Then("^Obtain (\\d+) transactions$")
    public void obtainTransactions(int transactionsSize) {
        assertEquals(transactionsSize, transactionList.size());
    }

    @AfterEach
    public void tearDown() {
        account = null;
    }

    private void assertTransaction(int transactionNumber, int sum, TransactionType type) {
        final Transaction transaction = transactionList.get(transactionNumber - 1);
        assertEquals(type, transaction.getType());
        assertEquals(Double.valueOf(sum), transaction.getAmount());
        assertEquals(account.getCbu(), transaction.getCbu());
    }

    @And("^Same account delete the (\\d+) transaction$")
    public void sameAccountDeleteTheTransaction(int transactionId) {
        deleteTransactionById((long) transactionId);
    }
}
