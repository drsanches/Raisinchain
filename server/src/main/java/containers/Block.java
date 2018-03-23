package containers;

import containersExceptions.BlockException;
import containersExceptions.TransactionException;
import org.json.JSONObject;

/**
 * @author Alexander Voroshilov
 */
public class Block {
    private TransactionsList transactions;
    private String hashCode;
    public static final int MAX_TRANSACTIONS_COUNT = 10;

    public Block(TransactionsList transactionsList, String hash) throws BlockException {
        if (transactionsList.sizeOfList() > MAX_TRANSACTIONS_COUNT)
            throw new BlockException("Too many transactions. Maximum count of transactions is " + MAX_TRANSACTIONS_COUNT);

        transactions = transactionsList;
        hashCode = hash;
    }

    public Block(String jsonObjectString) throws org.json.JSONException, TransactionException, BlockException {
        JSONObject jsonObject = new JSONObject(jsonObjectString);
        String transactionsJsonString = jsonObject.getJSONArray("Transactions").toString();
        TransactionsList transactionsList = new TransactionsList(transactionsJsonString);

        if (transactionsList.sizeOfList() > MAX_TRANSACTIONS_COUNT)
            throw new BlockException("Too many transactions. Maximum count of transactions is " + MAX_TRANSACTIONS_COUNT);

        transactions = transactionsList;
        hashCode = jsonObject.getString("Hash-code");
    }

    public static Block createFirstBlock() {
        //TODO: Think about it
        try {
            return new Block(TransactionsList.createFirstTransactionsList(), "First hash");
        }
        catch (BlockException e) {
            return null;
        }
    }

    /**
     * @author Marina Krylova
     */
    public boolean equals(Block b){
        return ((transactions.equals(b.transactions))&&(hashCode.equals(b.hashCode)));
    }

    public TransactionsList getTransactionsList() {
        return transactions;
    }

    public String getHashCode() {
        return hashCode;
    }

    public JSONObject getJsonObject() throws org.json.JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Transactions", transactions.getJsonArray());
        jsonObject.put("Hash-code", hashCode);
        return jsonObject;
    }

    /**
     * @author Irina Tokareva
     */
    public String calculateHashCode() {
        return String.valueOf(getJsonObject().toString().hashCode());
    }

    public boolean isCorrect(Block previousBlock) {
        return hashCode == previousBlock.calculateHashCode();
    }
}
