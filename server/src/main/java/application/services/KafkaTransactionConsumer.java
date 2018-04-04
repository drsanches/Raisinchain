package application.services;

import application.Application;
import containers.Transaction;
import containers.TransactionsList;
import containersExceptions.TransactionException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaTransactionConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    @KafkaListener(topics="transactions")
    public void processMessage(String content) throws IOException, TransactionException {
        log.info("received content = '{}'", content);
        TransactionsList transactionsList = Application.transactionsList;
        transactionsList.addTransaction(new Transaction(content));
        transactionsList.saveToJsonFile(Application.TRANSACTIONS_FILENAME);
    }
}
