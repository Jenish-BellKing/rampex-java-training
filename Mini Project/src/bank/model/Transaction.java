package bank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class Transaction {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String transactionId;
    private final TransactionType type;
    private final double amount;
    private final double balanceAfter;
    private final String description;
    private final LocalDateTime timestamp;

    public Transaction(TransactionType type, double amount, double balanceAfter, String description) {
        this.transactionId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.type          = type;
        this.amount        = amount;
        this.balanceAfter  = balanceAfter;
        this.description   = description;
        this.timestamp     = LocalDateTime.now();
    }

    public String getTransactionId()    { return transactionId; }
    public TransactionType getType()    { return type; }
    public double getAmount()           { return amount; }
    public double getBalanceAfter()     { return balanceAfter; }
    public String getDescription()      { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format(
            "[%s] %-20s | TxnID: %s | Amount: %+10.2f | Balance: %10.2f | %s",
            timestamp.format(FORMATTER),
            type.getDisplayName(),
            transactionId,
            (type == TransactionType.WITHDRAWAL || type == TransactionType.TRANSFER_OUT)
                ? -amount : amount,
            balanceAfter,
            description
        );
    }
}
