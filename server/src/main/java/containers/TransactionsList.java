package containers;

import containersExceptions.TransactionException;
import containersExceptions.TransactionsListException;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static containers.Transaction.*;

/**
 * @author Marina Krylova
 */

public class TransactionsList{

    private ArrayList<Transaction> transactions;

    public TransactionsList() {
        transactions = new ArrayList<Transaction>();
    }

    public TransactionsList(ArrayList<Transaction> tr) {
        transactions = tr;
    }

    //untested method
    public ArrayList<String> toArrayList(){

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<this.size(); i++){
            list.add(transactions.get(i).getTransaction());
        }
        return list;
    }

    public void addTransaction(Transaction tr) {

        transactions.add(tr);
    }

    public ArrayList<Transaction> getTransactions() {

        return transactions;
    }

    public boolean contains(Transaction tr){

        return transactions.contains(tr);
    }

    /**
    * @author Alexander Voroshilov
    */
    public TransactionsList(String jsonArrayString) throws org.json.JSONException, TransactionException {
        transactions = new ArrayList<Transaction>();
        JSONArray jsonArray = new JSONArray(jsonArrayString);
        for (int i = 0; i < jsonArray.length(); i++) {
            String transactionString = jsonArray.get(i).toString();
            Transaction transaction = new Transaction(transactionString);
            addTransaction(transaction);
        }
    }

    /**
     * @author Alexander Voroshilov
     */
    public JSONArray getJsonArray() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction transaction: transactions)
            jsonArray.put(transaction.getJsonString());

        return jsonArray;
    }

    /**
     * @author Alexander Voroshilov
     */
    public void removeTransaction(Transaction tr) throws TransactionsListException {
        if (transactions.indexOf(tr) == -1)
            throw new TransactionsListException("Transaction list does not contain this transaction.");
        transactions.remove(tr);
    }

    public int size(){
        return transactions.size();
    }

    @Override
    public boolean equals(Object tr) {
        TransactionsList trans = (TransactionsList) tr;
        if (this.size() == trans.size()){
            for (int i = 0; i<trans.size(); i++){
                if (!transactions.get(i).equals(trans.transactions.get(i))) return false;
            }
            return true;
        } else return false;
    }

    /**
     * @author Alexander Voroshilov
     */
    public static TransactionsList createFirstTransactionsList() {
        TransactionsList tr = new TransactionsList();
        tr.addTransaction(createFirstTransaction());
        return tr;
    }

    /**
     * @author Alexander Voroshilov
     */
    public void saveToJsonFile(String filename) throws java.io.IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(getJsonArray().toString());
        writer.close();
    }

    /**
     * @author Alexander Voroshilov
     */
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

