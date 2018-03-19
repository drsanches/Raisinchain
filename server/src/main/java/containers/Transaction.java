package containers;

public class Transaction{
    private String transaction;

    public Transaction(String tr){
        if (!tr.equals(null))
            transaction = tr;
    }

    public String getTransaction(){

        return transaction;
    }

    public String getJsonString() {

        return transaction;
    }
}
