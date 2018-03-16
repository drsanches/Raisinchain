package main;

import java.util.ArrayList;


public class TransactionList{
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    public boolean addTransaction(Transaction tr){
      try{
	if (!tr.equals(null))
        	transactions.add(tr);
        return true;
      }catch(Exception e){
        e.printStackTrace();
        return false;
      }
    }

    public boolean removeTransaction(Transaction tr){
      try{
        transactions.remove(tr);
        return true;
      }catch(Exception e){
        e.printStackTrace();
        return false;
      }
    }
}


class Transaction{
  private String value;

  public String getValue(){
    return value;
  }
  public void setValue(String trans){
    value = trans;
  }
}
