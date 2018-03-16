package containers;

import org.json.JSONObject;

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

    public String getJsonString() throws org.json.JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Transactions", transactions.getJsonString());
        jsonObject.put("Hash-code", hashCode);
        return jsonObject.toString();
    }

}
