package bank.exceptions;

public class AccountNotFoundException extends BankException {

    private final String accountNumber;

    public AccountNotFoundException(String accountNumber) {
        super("ERR_ACCOUNT_NOT_FOUND",
              String.format("Account not found: %s", accountNumber));
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
