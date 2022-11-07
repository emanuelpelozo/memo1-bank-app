Feature: Bank account operations
  Checking bank account operations

  Scenario: Successfully withdraw money when balance is enough
    Given Account with a balance of 1000
    When Trying to withdraw 500
    Then Account balance should be 500

  Scenario: Cannot withdraw more money than the account balance
    Given Account with a balance of 1000
    When Trying to withdraw 1001
    Then Operation should be denied due to insufficient funds
    And Account balance should remain 1000

  Scenario: Successfully deposit money when sum is not negative
    Given Account with a balance of 1000
    When Trying to deposit 500
    Then Account balance should be 1500

  Scenario: Cannot deposit money when sum is negative
    Given Account with a balance of 200
    When Trying to deposit -100
    Then Operation should be denied due to negative sum
    And Account balance should remain 200

  Scenario: Retrieve transactions for account with a deposit
    Given Account with a deposit transaction of 1000
    When Trying to retrieve transactions for account
    Then Obtain deposit transaction of 1000 for account

  Scenario: Retrieve transactions for account with a withdraw
    Given Account with a withdraw transaction of 100
    When Trying to retrieve transactions for account
    Then Obtain withdraw transaction of 100 for account

  Scenario: Retrieve transactions for a non existent account
    Given Non existent account
    When Trying to retrieve transactions for account
    Then Operation should fail due to non existent account

  Scenario: Retrieve transactions for account with a
            withdraw and a deposit with a deleted transaction
    Given Account with a withdraw transaction of 100
    And Same account with a deposit transaction of 1000
    And Same account delete the 1 transaction
    When Trying to retrieve transactions for account
    Then Obtain 1 transactions
    And Obtain deposit transaction of 1000 for account in 1 place