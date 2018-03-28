package containers;

import containersExceptions.BlockException;
import containersExceptions.TransactionException;
import org.json.JSONObject;
import sun.security.krb5.internal.crypto.Nonce;

import java.security.MessageDigest;

/**
 * @author Alexander Voroshilov
 */
public class Block {

    private TransactionsList transactions;
    private String hashCode;
    private int nonce = 0;
    public static final int MAX_TRANSACTIONS_COUNT = 10;

    public Block(TransactionsList transactionsList, String hash) throws BlockException {
        if (transactionsList.size() > MAX_TRANSACTIONS_COUNT)
            throw new BlockException("Too many transactions. Maximum count of transactions is " + MAX_TRANSACTIONS_COUNT);

        transactions = transactionsList;
        hashCode = hash;
        nonce = 0;
    }

    public Block(TransactionsList transactionsList, String hash, int newNonce) throws BlockException {
        this(transactionsList, hash);
        nonce = newNonce;
    }

    public Block(String jsonObjectString) throws org.json.JSONException, TransactionException, BlockException {
        JSONObject jsonObject = new JSONObject(jsonObjectString);
        String transactionsJsonString = jsonObject.getJSONArray("Transactions").toString();
        TransactionsList transactionsList = new TransactionsList(transactionsJsonString);

        if (transactionsList.size() > MAX_TRANSACTIONS_COUNT)
            throw new BlockException("Too many transactions. Maximum count of transactions is " + MAX_TRANSACTIONS_COUNT);

        transactions = transactionsList;
        hashCode = jsonObject.getString("Hash-code");
        nonce = jsonObject.getInt("Nonce");
    }

    public static Block createFirstBlock() {
        try {
            Block block = new Block(TransactionsList.createFirstTransactionsList(), "0000000000000000000000000000000000000000000000000000000000000000");
            block.mining();
            return block;
        }
        catch (BlockException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author Marina Krylova
     */
    @Override
    public boolean equals(Object b){
        return ((transactions.equals(((Block) b).transactions))&&(hashCode.equals(((Block) b).hashCode)));
    }

    public TransactionsList getTransactionsList() {
        return transactions;
    }

    public String getHashCode() {
        return hashCode;
    }

    public int getNonce() {
        return nonce;
    }

    public JSONObject getJsonObject() throws org.json.JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Transactions", transactions.getJsonArray());
        jsonObject.put("Hash-code", hashCode);
        jsonObject.put("Nonce", nonce);
        return jsonObject;
    }

    public String calculateHashCode() {
        String blockString = getJsonObject().toString();
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(blockString);
    }

    public void mining() {
        while (!isZerosCountCorrect(BlockChain.FIRST_ZEROS_COUNT)) {
            nonce++;
            System.out.println("Nonce: " + String.valueOf(nonce) + ", hash-code: " + calculateHashCode());
        }
    }

    public boolean isZerosCountCorrect(int firstZerosCount) {
        String thisHashCode = calculateHashCode();
        for (int i = 0; i < firstZerosCount; i++) {
            if (thisHashCode.charAt(i) != '0')
                return false;
        }
        return true;
    }

    public boolean isCorrect(Block previousBlock, int firstZerosCount) {
        return hashCode.equals(previousBlock.calculateHashCode()) && isZerosCountCorrect(firstZerosCount);
    }
}
