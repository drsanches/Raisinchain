package containers;

import org.json.JSONArray;

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

    public void addTransaction(Transaction tr){
        try{
            if (!tr.equals(null))
                transactions.add(tr);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void removeTransaction(Transaction tr){
      try{
        transactions.remove(tr);
      }catch(Exception e){
        e.printStackTrace();
      }
    }

    //this function creates a list of transactions for the first block of the chain
    public static TransactionsList createFirstTransactionsList(){
        TransactionsList tr = new TransactionsList();
        tr.addTransaction(new Transaction("Hello, world!"));
        return tr;
    }

    public String getJsonString() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction transaction: transactions)
            jsonArray.put(transaction.getJsonString());

        return jsonArray.toString();
    }
}

