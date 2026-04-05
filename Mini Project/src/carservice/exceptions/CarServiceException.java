package carservice.exceptions;

public class CarServiceException extends Exception {

    private final String errorCode;

    public CarServiceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CarServiceException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", errorCode, getMessage());
    }
}
