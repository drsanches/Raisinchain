package main;

import containers.BlockChain;
import containers.TransactionsList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.table.TableRowSorter;


@SpringBootApplication
public class Application {
    final static String TRANSACTIONS_FILENAME = "transactions.json";
    final static String BLOCKCHAIN_FILENAME = "blockchain.json";

    public static TransactionsList transactionsList = new TransactionsList();
    public static BlockChain blockChain = new BlockChain();

    public static void main(String[] args) {
        try {
        transactionsList.loadFromJsonFile(TRANSACTIONS_FILENAME);
        blockChain.loadFromJsonFile(BLOCKCHAIN_FILENAME);
        }
        catch(java.io.IOException e) {
            //TODO: write code
            System.out.println(e.toString());
        }
        catch(org.json.JSONException e) {
            //TODO: write code
            System.out.println(e.toString());
        }

        SpringApplication.run(Application.class, args);
    }


}