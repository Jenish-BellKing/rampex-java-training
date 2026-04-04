package bank.exceptions;

public class InvalidAmountException extends BankException {

    private final double amount;

    public InvalidAmountException(double amount) {
        super("ERR_INVALID_AMOUNT",
              String.format("Amount must be positive. Received: %.2f", amount));
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
