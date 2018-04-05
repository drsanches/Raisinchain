package application.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Marina Krylova
 */
@Service
public class KafkaBlockProducer {

    @Autowired
    private KafkaTemplate<String, String> BlockProducer;

    String kafkaTopic = "blocks";

    public void send(String data) {

        BlockProducer.send(kafkaTopic, data);
    }
}