package use_case;

/**
 * Exception thrown when there is an error during data access operations.
 * This can be used to encapsulate specific issues related to data retrieval,
 * storage, or processing in the data access layer.
 */
public class DataAccessException extends Exception {
    public DataAccessException(String message) {
        super(message);
    }
}
