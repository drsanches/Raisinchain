package containers;

import containersExceptions.TransactionException;

/**
 * @author Marina Krylova
 */

public class Transaction{

    private String transaction;



    /**
     * @author Alexander Voroshilov
     */
    public Transaction(String tr) throws TransactionException {
        if (tr == null)
            throw new TransactionException("Incorrect transaction");
        transaction = tr;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getJsonString() {
        return transaction;
    }

    /**
     * @author Alexander Voroshilov
     */
    public static Transaction createFirstTransaction() {
        //TODO: Think about it
        try {
            return new Transaction("First transaction");
        }
        catch (TransactionException e) {
            return null;
        }
    }

    public boolean equals(Transaction tr) {
        return transaction.equals(tr.transaction);
    }
}
