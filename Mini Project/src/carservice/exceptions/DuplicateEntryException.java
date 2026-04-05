package carservice.exceptions;

public class DuplicateEntryException extends CarServiceException {

    public DuplicateEntryException(String entityType, String identifier) {
        super("ERR_DUPLICATE_ENTRY",
              String.format("%s already exists with identifier: %s", entityType, identifier));
    }
}
