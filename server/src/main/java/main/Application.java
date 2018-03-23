package main;

import containers.BlockChain;
import containers.RandomContainerCreator;
import containers.TransactionsList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Alexander Voroshilov
 */
@SpringBootApplication
public class Application {
    public final static String TRANSACTIONS_FILENAME = "transactions.json";
    public final static String BLOCKCHAIN_FILENAME = "blockchain.json";

    public static TransactionsList transactionsList = new TransactionsList();
    public static BlockChain blockChain = new BlockChain();

    public static void main(String[] args) {
        try {
            transactionsList.loadFromJsonFile(TRANSACTIONS_FILENAME);
            blockChain.loadFromJsonFile(BLOCKCHAIN_FILENAME);
        }
        catch(Exception e) {
            //TODO: write code
            System.err.println(e.getMessage());
        }

        SpringApplication.run(Application.class, args);
    }
}