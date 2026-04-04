package bank.service;

import bank.account.Account;
import bank.account.CurrentAccount;
import bank.account.FixedDepositAccount;
import bank.account.SavingsAccount;
import bank.exceptions.AccountNotFoundException;
import bank.exceptions.BankException;
import bank.exceptions.InvalidAmountException;
import bank.model.AccountHolder;

import java.util.*;

public class Bank {

    private final String bankName;
    private final Map<String, Account> accounts;

    public Bank(String bankName) {
        if (bankName == null || bankName.isBlank()) {
            throw new IllegalArgumentException("Bank name cannot be blank.");
        }
        this.bankName = bankName.trim();
        this.accounts = new LinkedHashMap<>();
    }

    public SavingsAccount openSavingsAccount(AccountHolder holder, double initialDeposit)
            throws InvalidAmountException {
        SavingsAccount account = new SavingsAccount(holder, initialDeposit);
        register(account);
        printOpenedBanner(account);
        return account;
    }

    public SavingsAccount openSavingsAccount(AccountHolder holder, double initialDeposit,
                                             double minBalance, double interestRate)
            throws InvalidAmountException {
        SavingsAccount account = new SavingsAccount(holder, initialDeposit, minBalance, interestRate);
        register(account);
        printOpenedBanner(account);
        return account;
    }

    public CurrentAccount openCurrentAccount(AccountHolder holder, double initialDeposit)
            throws InvalidAmountException {
        CurrentAccount account = new CurrentAccount(holder, initialDeposit);
        register(account);
        printOpenedBanner(account);
        return account;
    }

    public CurrentAccount openCurrentAccount(AccountHolder holder, double initialDeposit,
                                             double overdraftLimit, double txnLimit)
            throws InvalidAmountException {
        CurrentAccount account = new CurrentAccount(holder, initialDeposit, overdraftLimit, txnLimit);
        register(account);
        printOpenedBanner(account);
        return account;
    }

    public FixedDepositAccount openFixedDeposit(AccountHolder holder, double principal,
                                                int tenureMonths)
            throws InvalidAmountException {
        FixedDepositAccount account = new FixedDepositAccount(holder, principal, tenureMonths);
        register(account);
        printOpenedBanner(account);
        return account;
    }

    public FixedDepositAccount openFixedDeposit(AccountHolder holder, double principal,
                                                int tenureMonths, double interestRate)
            throws InvalidAmountException {
        FixedDepositAccount account = new FixedDepositAccount(holder, principal,
                                                              tenureMonths, interestRate);
        register(account);
        printOpenedBanner(account);
        return account;
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

    public void applyInterestToAll() {
        System.out.println("\n══ Applying interest to all accounts ══");
        for (Account account : accounts.values()) {
            if (account.isActive()) {
                try {
                    account.calculateInterest();
                    System.out.printf("  %s — Interest applied.%n", account.getAccountNumber());
                } catch (BankException e) {
                    System.out.printf("  %s — Skipped: %s%n", account.getAccountNumber(), e.getMessage());
                }
            }
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount)
            throws BankException {
        Account from = findAccount(fromAccountNumber);
        Account to   = findAccount(toAccountNumber);
        from.transferTo(to, amount);
        System.out.printf("  ✔ Transferred %.2f from %s → %s%n",
                          amount, fromAccountNumber, toAccountNumber);
    }

    public void listAllAccounts() {
        System.out.println("\n══ " + bankName + " — All Accounts ══");
        if (accounts.isEmpty()) {
            System.out.println("  No accounts registered.");
            return;
        }
        accounts.values().forEach(a -> System.out.println("  " + a));
    }

    public String getBankName()      { return bankName; }
    public int    getTotalAccounts() { return accounts.size(); }

    private void register(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    private void printOpenedBanner(Account account) {
        System.out.printf("  ✔ Account opened: %-10s | %-12s | Holder: %s%n",
                          account.getAccountNumber(),
                          account.getClass().getSimpleName(),
                          account.getHolder().getName());
    }
}
