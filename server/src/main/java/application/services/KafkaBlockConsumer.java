package application.services;

import application.Application;
import containers.Block;
import containers.Transaction;
import containers.TransactionsList;
import containersExceptions.BlockChainException;
import containersExceptions.BlockException;
import containersExceptions.TransactionException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Marina Krylova
 */
@Component
public class KafkaBlockConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaBlockConsumer.class);

    @KafkaListener(topics="blocks")
    public void processMessage(String content) throws TransactionException, BlockException, BlockChainException {
        log.info("received content = '{}'", content);
        Application.blockChain.add(new Block(content));
//        transactionsList.saveToJsonFile(Application.TRANSACTIONS_FILENAME);
    }
}
