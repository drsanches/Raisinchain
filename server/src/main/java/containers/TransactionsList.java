package containers;

import org.json.JSONArray;

import javax.transaction.TransactionRequiredException;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TransactionsList{
    private ArrayList<Transaction> transactions;

    //constructors for lists of transactions
    public TransactionsList() {
        transactions = new ArrayList<Transaction>();
    }

    public TransactionsList(ArrayList<Transaction> tr) {
        transactions = tr;
    }

    public TransactionsList(String jsonArrayString) throws org.json.JSONException, TransactionException {
        transactions = new ArrayList<Transaction>();
        JSONArray jsonArray = new JSONArray(jsonArrayString);
        for (int i = 0; i < jsonArray.length(); i++) {
            String transactionString = jsonArray.get(i).toString();
            Transaction transaction = new Transaction(transactionString);
            addTransaction(transaction);
        }
    }

    //this function creates a list of transactions for the first block of the chain
    public static TransactionsList createFirstTransactionsList() {
        TransactionsList tr = new TransactionsList();
        tr.addTransaction(Transaction.createFirstTransaction());
        return tr;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction tr) {
        transactions.add(tr);
    }

    public void removeTransaction(Transaction tr) throws TransactionListException {
        if (transactions.indexOf(tr) == -1)
            throw new TransactionListException("Transaction list does not contain this transaction.");
        transactions.remove(tr);
    }

    public JSONArray getJsonArray() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction transaction: transactions)
            jsonArray.put(transaction.getJsonString());

        return jsonArray;
    }

    public void saveToJsonFile(String filename) throws java.io.IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(getJsonArray().toString());
        writer.close();
    }

    public void loadFromJsonFile(String filename) throws java.io.IOException, org.json.JSONException, TransactionException {
        transactions.clear();
        String jsonString = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            String transactionString = jsonArray.get(i).toString();
            Transaction newTransaction = new Transaction(transactionString);
            addTransaction(newTransaction);
        }
    }
}

class TransactionListException extends Exception {
    public TransactionListException(String message) {
        super(message);
    }
}
