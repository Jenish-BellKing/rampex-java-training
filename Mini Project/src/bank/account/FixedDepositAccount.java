package bank.account;

import bank.exceptions.BankException;
import bank.exceptions.InvalidAmountException;
import bank.model.AccountHolder;
import bank.model.TransactionType;

public class FixedDepositAccount extends Account {

    public static final double DEFAULT_INTEREST_RATE = 7.5;
    private static final int   MONTHS_IN_YEAR        = 12;

    private final double annualInterestRate;
    private final int    tenureMonths;
    private       boolean matured;

    public FixedDepositAccount(AccountHolder holder, double principalAmount,
                               int tenureMonths, double annualInterestRate)
            throws InvalidAmountException {

        super(holder, principalAmount);
        if (tenureMonths <= 0) {
            throw new IllegalArgumentException("Tenure must be at least 1 month.");
        }
        if (annualInterestRate <= 0 || annualInterestRate > 100) {
            throw new IllegalArgumentException("Interest rate must be between 0 and 100.");
        }
        this.annualInterestRate = annualInterestRate;
        this.tenureMonths       = tenureMonths;
        this.matured            = false;
    }

    public FixedDepositAccount(AccountHolder holder, double principalAmount, int tenureMonths)
            throws InvalidAmountException {
        this(holder, principalAmount, tenureMonths, DEFAULT_INTEREST_RATE);
    }

    @Override
    public void withdraw(double amount) throws BankException {
        ensureAccountIsActive();
        if (!matured) {
            throw new BankException("ERR_FD_LOCKED",
                String.format(
                    "Fixed Deposit is locked for %d months. Premature withdrawal not allowed.",
                    tenureMonths));
        }
        validateAmount(amount);
        if (amount > getBalance()) {
            throw new BankException("ERR_INSUFFICIENT_FUNDS",
                String.format("Withdrawal (%.2f) exceeds matured FD balance (%.2f).",
                              amount, getBalance()));
        }
        deductBalance(amount);
        recordTransaction(TransactionType.WITHDRAWAL, amount, "FD maturity withdrawal");
    }

    @Override
    public void calculateInterest() throws BankException {
        ensureAccountIsActive();
        if (matured) {
            throw new BankException("ERR_FD_ALREADY_MATURED",
                "Interest has already been credited for this Fixed Deposit.");
        }
        double principal = getBalance();
        double timeYears = (double) tenureMonths / MONTHS_IN_YEAR;
        double interest  = principal * (annualInterestRate / 100.0) * timeYears;

        addBalance(interest);
        recordTransaction(TransactionType.INTEREST_CREDIT, interest,
                          String.format("FD maturity interest at %.2f%% for %d months",
                                        annualInterestRate, tenureMonths));
        this.matured = true;
        System.out.printf("  ✔ FD matured! Interest credited: %.2f | Total payout: %.2f%n",
                          interest, getBalance());
    }

    @Override
    public String getAccountTypeSummary() {
        return String.format(
            "FIXED DEPOSIT | Principal: %.2f | Rate: %.2f%% p.a. | Tenure: %d months | Status: %s",
            getTransactionHistory().get(0).getAmount(),
            annualInterestRate,
            tenureMonths,
            matured ? "MATURED" : "LOCKED");
    }

    public double getAnnualInterestRate() { return annualInterestRate; }
    public int    getTenureMonths()       { return tenureMonths; }
    public boolean isMatured()            { return matured; }
}
