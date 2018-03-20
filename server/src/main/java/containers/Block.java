package containers;

import containersExceptions.TransactionException;
import org.json.JSONObject;

/**
 * @author Alexander Voroshilov
 */

public class Block {
    private TransactionsList transactions;
    private String hashCode;

    public Block(TransactionsList trs, String hash) {
        transactions = trs;
        hashCode = hash;
    }

    public Block(String jsonObjectString) throws org.json.JSONException, TransactionException {
        JSONObject jsonObject = new JSONObject(jsonObjectString);
        String transactionsJsonString = jsonObject.getJSONArray("Transactions").toString();
        transactions = new TransactionsList(transactionsJsonString);
        hashCode = jsonObject.getString("Hash-code");
    }

    public static Block createFirstBlock() {
        return new Block(TransactionsList.createFirstTransactionsList(), "First hash");
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
}
