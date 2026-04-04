package bank.account;

import bank.exceptions.BankException;
import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InvalidAmountException;
import bank.exceptions.TransactionLimitExceededException;
import bank.model.AccountHolder;
import bank.model.TransactionType;

public class CurrentAccount extends Account {

    public static final double DEFAULT_OVERDRAFT_LIMIT   = 5000.0;
    public static final double DEFAULT_TRANSACTION_LIMIT = 50000.0;

    private double overdraftLimit;
    private final double dailyTransactionLimit;

    public CurrentAccount(AccountHolder holder, double initialDeposit,
                          double overdraftLimit, double dailyTransactionLimit)
            throws InvalidAmountException {

        super(holder, initialDeposit);
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        if (dailyTransactionLimit <= 0) {
            throw new IllegalArgumentException("Daily transaction limit must be positive.");
        }
        this.overdraftLimit        = overdraftLimit;
        this.dailyTransactionLimit = dailyTransactionLimit;
    }

    public CurrentAccount(AccountHolder holder, double initialDeposit)
            throws InvalidAmountException {
        this(holder, initialDeposit, DEFAULT_OVERDRAFT_LIMIT, DEFAULT_TRANSACTION_LIMIT);
    }

    @Override
    public void withdraw(double amount) throws BankException {
        ensureAccountIsActive();
        validateAmount(amount);

        if (amount > dailyTransactionLimit) {
            throw new TransactionLimitExceededException(dailyTransactionLimit, amount);
        }

        double effectiveAvailable = getBalance() + overdraftLimit;
        if (amount > effectiveAvailable) {
            throw new InsufficientFundsException(effectiveAvailable, amount);
        }

        deductBalance(amount);
        String note = getBalance() < 0
                ? String.format("Cash withdrawal [Overdraft in use: %.2f]", -getBalance())
                : "Cash withdrawal";
        recordTransaction(TransactionType.WITHDRAWAL, amount, note);
    }

    @Override
    public void calculateInterest() throws BankException {
        System.out.println("INFO: Current accounts do not accrue interest.");
    }

    @Override
    public String getAccountTypeSummary() {
        return String.format(
            "CURRENT ACCOUNT | Overdraft Limit: %.2f | Daily Txn Limit: %.2f | Overdraft Used: %.2f",
            overdraftLimit,
            dailyTransactionLimit,
            Math.max(0, -getBalance()));
    }

    public double getOverdraftLimit()        { return overdraftLimit; }
    public double getDailyTransactionLimit() { return dailyTransactionLimit; }

    public double getEffectiveAvailableBalance() {
        return getBalance() + overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = overdraftLimit;
    }
}
