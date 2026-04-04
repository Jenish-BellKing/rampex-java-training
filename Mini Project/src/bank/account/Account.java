package bank.account;

import bank.exceptions.BankException;
import bank.exceptions.InvalidAmountException;
import bank.model.AccountHolder;
import bank.model.Transaction;
import bank.model.TransactionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Account {

    private static final AtomicLong SEQUENCE = new AtomicLong(100001L);

    private final String accountNumber;
    private final AccountHolder holder;
    private final LocalDate openedDate;
    private double balance;
    private boolean active;

    private final List<Transaction> transactionHistory;

    protected Account(AccountHolder holder, double initialDeposit) throws InvalidAmountException {
        if (holder == null) {
            throw new IllegalArgumentException("Account holder cannot be null.");
        }
        validateAmount(initialDeposit);

        this.accountNumber      = generateAccountNumber();
        this.holder             = holder;
        this.balance            = initialDeposit;
        this.openedDate         = LocalDate.now();
        this.active             = true;
        this.transactionHistory = new ArrayList<>();

        recordTransaction(TransactionType.DEPOSIT, initialDeposit, "Account opening deposit");
    }

    public abstract void withdraw(double amount) throws BankException;

    public abstract void calculateInterest() throws BankException;

    public abstract String getAccountTypeSummary();

    public final void deposit(double amount) throws InvalidAmountException {
        ensureAccountIsActive();
        validateAmount(amount);
        balance += amount;
        recordTransaction(TransactionType.DEPOSIT, amount, "Cash deposit");
    }

    public final void transferTo(Account beneficiary, double amount) throws BankException {
        ensureAccountIsActive();
        beneficiary.ensureAccountIsActive();
        validateAmount(amount);

        withdraw(amount);

        transactionHistory.remove(transactionHistory.size() - 1);
        recordTransaction(TransactionType.TRANSFER_OUT, amount,
                          "Transfer to " + beneficiary.getAccountNumber());

        beneficiary.balance += amount;
        beneficiary.recordTransaction(TransactionType.TRANSFER_IN, amount,
                                      "Transfer from " + this.accountNumber);
    }

    public final List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    public final void printStatement() {
        System.out.println("═".repeat(100));
        System.out.printf("  ACCOUNT STATEMENT — %s (%s)%n",
                          accountNumber, getClass().getSimpleName());
        System.out.printf("  Holder : %s%n", holder.getName());
        System.out.printf("  Opened : %s%n", openedDate);
        System.out.printf("  Status : %s%n", active ? "ACTIVE" : "CLOSED");
        System.out.println("─".repeat(100));
        System.out.printf("  %-20s│ %-20s│ %-10s│ %-12s│ %-12s│ Description%n",
                          "Date & Time", "Type", "Txn ID", "Amount", "Balance");
        System.out.println("─".repeat(100));
        for (Transaction t : transactionHistory) {
            System.out.println("  " + t);
        }
        System.out.println("─".repeat(100));
        System.out.printf("  Current Balance: %.2f%n", balance);
        System.out.println("═".repeat(100));
    }

    protected void deductBalance(double amount) {
        this.balance -= amount;
    }

    protected void addBalance(double amount) {
        this.balance += amount;
    }

    protected void recordTransaction(TransactionType type, double amount, String description) {
        transactionHistory.add(new Transaction(type, amount, balance, description));
    }

    protected void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
    }

    protected void ensureAccountIsActive() {
        if (!active) {
            throw new IllegalStateException(
                "Account " + accountNumber + " is closed and cannot perform transactions.");
        }
    }

    public String getAccountNumber()  { return accountNumber; }
    public AccountHolder getHolder()  { return holder; }
    public double getBalance()        { return balance; }
    public LocalDate getOpenedDate()  { return openedDate; }
    public boolean isActive()         { return active; }

    public void closeAccount() {
        this.active = false;
    }

    private static String generateAccountNumber() {
        return "ACC" + SEQUENCE.getAndIncrement();
    }

    @Override
    public String toString() {
        return String.format("%s | %s | Holder: %-20s | Balance: %12.2f | Status: %s",
                             getClass().getSimpleName(),
                             accountNumber,
                             holder.getName(),
                             balance,
                             active ? "ACTIVE" : "CLOSED");
    }
}
