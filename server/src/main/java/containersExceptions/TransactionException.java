package containersExceptions;

/**
 * @author Alexander Voroshilov
 */

public class TransactionException extends Exception {
    public TransactionException(String message) {
        super(message);
    }
}
