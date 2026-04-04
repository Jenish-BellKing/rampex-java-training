package bank.account;

import bank.exceptions.BankException;
import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InvalidAmountException;
import bank.model.AccountHolder;
import bank.model.TransactionType;

public class SavingsAccount extends Account {

    public static final double DEFAULT_INTEREST_RATE   = 4.5;
    public static final double DEFAULT_MINIMUM_BALANCE = 1000.0;

    private final double minimumBalance;
    private double annualInterestRate;

    public SavingsAccount(AccountHolder holder, double initialDeposit,
                          double minimumBalance, double annualInterestRate)
            throws InvalidAmountException {

        super(holder, initialDeposit);
        if (minimumBalance < 0) {
            throw new IllegalArgumentException("Minimum balance cannot be negative.");
        }
        if (annualInterestRate < 0 || annualInterestRate > 100) {
            throw new IllegalArgumentException("Interest rate must be between 0 and 100.");
        }
        if (initialDeposit < minimumBalance) {
            throw new IllegalArgumentException(
                String.format("Initial deposit (%.2f) must be >= minimum balance (%.2f).",
                              initialDeposit, minimumBalance));
        }
        this.minimumBalance     = minimumBalance;
        this.annualInterestRate = annualInterestRate;
    }

    public SavingsAccount(AccountHolder holder, double initialDeposit)
            throws InvalidAmountException {
        this(holder, initialDeposit, DEFAULT_MINIMUM_BALANCE, DEFAULT_INTEREST_RATE);
    }

    @Override
    public void withdraw(double amount) throws BankException {
        ensureAccountIsActive();
        validateAmount(amount);

        double projectedBalance = getBalance() - amount;
        if (projectedBalance < minimumBalance) {
            throw new InsufficientFundsException(
                getBalance() - minimumBalance,
                amount
            );
        }
        deductBalance(amount);
        recordTransaction(TransactionType.WITHDRAWAL, amount, "Cash withdrawal");
    }

    @Override
    public void calculateInterest() throws BankException {
        ensureAccountIsActive();
        double interest = getBalance() * (annualInterestRate / 100.0);
        addBalance(interest);
        recordTransaction(TransactionType.INTEREST_CREDIT, interest,
                          String.format("Annual interest at %.2f%%", annualInterestRate));
    }

    @Override
    public String getAccountTypeSummary() {
        return String.format(
            "SAVINGS ACCOUNT | Min Balance: %.2f | Interest Rate: %.2f%% p.a.",
            minimumBalance, annualInterestRate);
    }

    public double getMinimumBalance()     { return minimumBalance; }
    public double getAnnualInterestRate() { return annualInterestRate; }

    public void setAnnualInterestRate(double rate) {
        if (rate < 0 || rate > 100) {
            throw new IllegalArgumentException("Interest rate must be between 0 and 100.");
        }
        this.annualInterestRate = rate;
    }
}
