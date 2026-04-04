package bank.exceptions;

public class TransactionLimitExceededException extends BankException {

    private final double limit;
    private final double attempted;

    public TransactionLimitExceededException(double limit, double attempted) {
        super("ERR_TRANSACTION_LIMIT",
              String.format("Transaction limit exceeded. Limit: %.2f, Attempted: %.2f",
                            limit, attempted));
        this.limit = limit;
        this.attempted = attempted;
    }

    public double getLimit() {
        return limit;
    }

    public double getAttempted() {
        return attempted;
    }
}
