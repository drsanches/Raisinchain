package containers;

import containers_exceptions.TransactionException;

public class Transaction{
    private String transaction;

    public Transaction(String tr) throws TransactionException {
        if (tr == null)
            throw new TransactionException("Incorrect transaction");
        transaction = tr;
    }
    
    public boolean equals(Transaction tr){
        
        return transaction.equals(tr.transaction);
    }

    public String getTransaction() {
        return transaction;
    }

    public String getJsonString() {
        return transaction;
    }

    public static Transaction createFirstTransaction()
    {
        //TODO: Think about it
        try {
            return new Transaction("First transaction");
        }
        catch (TransactionException e) {
            return null;
        }
    }
}

