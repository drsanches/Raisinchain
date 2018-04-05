package application;

import application.services.KafkaBlockConsumer;
import application.services.KafkaBlockProducer;
import application.services.KafkaTransactionProducer;
import containers.Block;
import containers.BlockChain;
import containers.TransactionsList;
import containersExceptions.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Alexander Voroshilov
 */
@SpringBootApplication
public class Application {
    public final static String TRANSACTIONS_FILENAME = "server/transactions.json";
    public final static String BLOCKCHAIN_FILENAME = "server/blockchain.json";

    public static TransactionsList transactionsList = new TransactionsList();
    public static BlockChain blockChain = new BlockChain();


    public static void main(String[] args) throws BlockException {

        SpringApplication.run(Application.class, args);


    }
}