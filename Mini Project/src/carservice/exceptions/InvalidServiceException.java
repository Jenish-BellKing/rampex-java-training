package carservice.exceptions;

public class InvalidServiceException extends CarServiceException {

    public InvalidServiceException(String message) {
        super("ERR_INVALID_SERVICE", message);
    }
}
