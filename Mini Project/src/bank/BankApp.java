package bank;

import bank.account.CurrentAccount;
import bank.account.FixedDepositAccount;
import bank.account.SavingsAccount;
import bank.exceptions.BankException;
import bank.model.AccountHolder;
import bank.service.Bank;

public class BankApp {

    public static void main(String[] args) {
        Bank bank = new Bank("Apex National Bank");

        section("SETUP — Create Account Holders");
        AccountHolder alice = new AccountHolder("Alice Johnson",  "alice@example.com",  "+919876543210", "ID-001");
        AccountHolder bob   = new AccountHolder("Bob Martinez",   "bob@example.com",    "+919876543211", "ID-002");
        AccountHolder carol = new AccountHolder("Carol Williams", "carol@example.com",  "+919876543212", "ID-003");
        System.out.println("  Created: " + alice);
        System.out.println("  Created: " + bob);
        System.out.println("  Created: " + carol);

        section("TEST 1 — Open Accounts");
        SavingsAccount aliceSavings = null;
        CurrentAccount bobCurrent   = null;
        FixedDepositAccount carolFD = null;

        try {
            aliceSavings = bank.openSavingsAccount(alice, 5000.0);
            bobCurrent   = bank.openCurrentAccount(bob,   10000.0);
            carolFD      = bank.openFixedDeposit(carol,   50000.0, 12, 8.5);
        } catch (BankException e) {
            System.err.println("UNEXPECTED: " + e);
            return;
        }

        bank.listAllAccounts();

        section("TEST 2 — Deposits (Normal)");
        try {
            aliceSavings.deposit(2000.0);
            System.out.printf("  Alice deposited 2000.00 | New balance: %.2f%n", aliceSavings.getBalance());
            bobCurrent.deposit(5000.0);
            System.out.printf("  Bob deposited 5000.00 | New balance: %.2f%n", bobCurrent.getBalance());
        } catch (BankException e) {
            System.err.println("UNEXPECTED: " + e);
        }

        section("TEST 3 — Withdrawals (Normal + Polymorphic Rules)");
        try {
            aliceSavings.withdraw(3000.0);
            System.out.printf("  Alice withdrew 3000.00 | Balance: %.2f (min: %.2f)%n",
                              aliceSavings.getBalance(), aliceSavings.getMinimumBalance());
        } catch (BankException e) {
            System.err.println("UNEXPECTED: " + e);
        }
        try {
            bobCurrent.withdraw(13000.0);
            System.out.printf("  Bob withdrew 13000.00 | Balance: %.2f (overdraft active)%n",
                              bobCurrent.getBalance());
        } catch (BankException e) {
            System.err.println("UNEXPECTED: " + e);
        }

        section("TEST 4 — Edge Case: Savings Min Balance Violation");
        try {
            aliceSavings.withdraw(3500.0);
            System.err.println("  ERROR: Should have thrown InsufficientFundsException!");
        } catch (BankException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 5 — Failure Case: Invalid Amounts");
        try {
            aliceSavings.deposit(-500.0);
            System.err.println("  ERROR: Should have thrown InvalidAmountException!");
        } catch (BankException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }
        try {
            bobCurrent.withdraw(0);
            System.err.println("  ERROR: Should have thrown InvalidAmountException!");
        } catch (BankException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 6 — Failure Case: FD Withdrawal Before Maturity");
        try {
            carolFD.withdraw(10000.0);
            System.err.println("  ERROR: Should have thrown BankException for locked FD!");
        } catch (BankException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 7 — Failure Case: Current Account Daily Transaction Limit");
        try {
            bobCurrent.deposit(100000.0);
            bobCurrent.withdraw(60000.0);
            System.err.println("  ERROR: Should have thrown TransactionLimitExceededException!");
        } catch (BankException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 8 — Failure Case: Account Not Found");
        try {
            bank.findAccount("ACC999999");
            System.err.println("  ERROR: Should have thrown AccountNotFoundException!");
        } catch (BankException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 9 — Advanced Feature: Account Transfer");
        try { aliceSavings.deposit(3000.0); } catch (BankException e) { System.err.println(e); }
        System.out.printf("  Before — Alice: %.2f | Bob: %.2f%n",
                          aliceSavings.getBalance(), bobCurrent.getBalance());
        try {
            bank.transfer(aliceSavings.getAccountNumber(),
                          bobCurrent.getAccountNumber(), 1500.0);
        } catch (BankException e) {
            System.err.println("UNEXPECTED: " + e);
        }
        System.out.printf("  After  — Alice: %.2f | Bob: %.2f%n",
                          aliceSavings.getBalance(), bobCurrent.getBalance());
        System.out.println("  [Edge] Transfer beyond available funds:");
        try {
            bank.transfer(aliceSavings.getAccountNumber(),
                          bobCurrent.getAccountNumber(), 10000.0);
            System.err.println("  ERROR: Should have thrown!");
        } catch (BankException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 10 — Polymorphic Interest Calculation");
        try {
            carolFD.calculateInterest();
        } catch (BankException e) {
            System.err.println("UNEXPECTED: " + e);
        }
        bank.applyInterestToAll();

        section("TEST 11 — FD Withdrawal After Maturity");
        System.out.printf("  Carol FD balance after maturity: %.2f%n", carolFD.getBalance());
        try {
            carolFD.withdraw(50000.0);
            System.out.printf("  Carol withdrew 50000.00 | Remaining: %.2f%n", carolFD.getBalance());
        } catch (BankException e) {
            System.err.println("UNEXPECTED: " + e);
        }

        section("TEST 12 — Failure Case: Invalid Account Holder Data");
        try {
            new AccountHolder("", "not-an-email", "123", "ID-BAD");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✔ Caught expected IllegalArgumentException: " + e.getMessage());
        }

        section("TEST 13 — Full Account Statements");
        aliceSavings.printStatement();
        System.out.println();
        bobCurrent.printStatement();
        System.out.println();
        carolFD.printStatement();

        bank.listAllAccounts();
    }

    private static void section(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.printf( "║  %-56s║%n", title);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
}
