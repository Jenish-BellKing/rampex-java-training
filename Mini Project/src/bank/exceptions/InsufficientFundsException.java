package bank.exceptions;

public class InsufficientFundsException extends BankException {

    private final double availableBalance;
    private final double requestedAmount;

    public InsufficientFundsException(double availableBalance, double requestedAmount) {
        super("ERR_INSUFFICIENT_FUNDS",
              String.format("Insufficient funds. Requested: %.2f, Available: %.2f",
                            requestedAmount, availableBalance));
        this.availableBalance = availableBalance;
        this.requestedAmount = requestedAmount;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }
}
