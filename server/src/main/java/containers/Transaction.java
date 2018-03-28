package containers;

import containersExceptions.TransactionException;

/**
 * @author Marina Krylova
 */

public class Transaction{

    private String transaction;


    public String getTransaction() {
        return transaction;
    }

    public String getJsonString() {
        return transaction;
    }



    /**
     * @author Alexander Voroshilov
     */
    public Transaction(String tr) throws TransactionException {
        if (tr == null)
            throw new TransactionException("Incorrect transaction");
        transaction = tr;
    }

    /**
     * @author Alexander Voroshilov
     */
    public static Transaction createFirstTransaction() {
        try {
            return new Transaction("First transaction");
        }
        catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object tr) {
        return transaction.equals(((Transaction) tr).transaction);
    }
}
