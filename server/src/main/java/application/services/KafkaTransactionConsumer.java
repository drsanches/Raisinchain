package application.services;

import application.Application;
import containers.Transaction;
import containers.TransactionsList;
import containersExceptions.TransactionException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Properties;


/**
 * @author Marina Krylova
 */
@Component
public class KafkaTransactionConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaTransactionConsumer.class);

    @KafkaListener(topics="transactions")
    public void processMessage(String content) throws IOException, TransactionException {
        log.info("received content = '{}'", content);

//        Producer<String, String> producer = createProducer();
//        try {
//            final ProducerRecord<String, String> record =
//                    new ProducerRecord<>("transactions", "Hello Mom " + content);
//
//            producer.send(record);
//        } finally {
//            producer.flush();
//            producer.close();
//        }

        TransactionsList transactionsList = Application.transactionsList;
        transactionsList.addTransaction(new Transaction(content));
//        transactionsList.saveToJsonFile(Application.TRANSACTIONS_FILENAME);
    }

    private static Producer<String, String> createProducer() {
        Properties props = new Properties();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.204.16:9092");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.1:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExffampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

}
