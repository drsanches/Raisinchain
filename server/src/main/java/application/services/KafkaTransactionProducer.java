package application.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaTransactionProducer {

    @Autowired
    private KafkaTemplate<String, String> TransactionProducer;

    String kafkaTopic = "transactions";

    public void send(String data) {

        TransactionProducer.send(kafkaTopic, data);
    }
}