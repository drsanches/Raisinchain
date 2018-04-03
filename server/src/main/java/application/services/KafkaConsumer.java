package application.services;

import application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

	@KafkaListener(topics="${kafka.topic}")
    public void processMessage(String message) {
		log.info("received content = '{}'", message);
        Application.a += message.length();
    }
}
