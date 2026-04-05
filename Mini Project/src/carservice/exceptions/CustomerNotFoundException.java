package carservice.exceptions;

public class CustomerNotFoundException extends CarServiceException {

    private final String customerId;

    public CustomerNotFoundException(String customerId) {
        super("ERR_CUSTOMER_NOT_FOUND",
              String.format("Customer not found with ID: %s", customerId));
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
