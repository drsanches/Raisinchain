package containers;

public class Block {
    private TransactionsList transactions;
    private String hashCode;

    public Block(TransactionsList trs, String hash) {
        transactions = trs;
        hashCode = hash;
    }

    public String getHashCode() {
        return hashCode;
    }

    public TransactionsList getTransactions() {
        return transactions;
    }

    public static Block createFirstBlock()
    {
        return new Block(TransactionsList.createFirstTransactionsList(), "");
    }
}
