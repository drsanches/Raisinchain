package application.services;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;


@Service
public class Broadcaster {
    private final Consumer<String, String> consumer;

    public Broadcaster() {
        consumer = createConsumer();
    }

    @PostConstruct
    public void init() {
        new Thread(this::run).start();
    }

    public void run() {
        System.out.println("--- consumer started ---");
        final int giveUp = 100;   int noRecordsCount = 0;

        while (true) {
//            System.out.println("--- consumer poll ---");

            try {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
//                System.out.println();

                consumerRecords.forEach(record -> {
                    System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                            record.key(), record.value(),
                            record.partition(), record.offset());
                });
            } catch (Throwable e) {
                e.printStackTrace();

            }

            consumer.commitAsync();
        }
    }

    @PreDestroy
    public void destroy() {
        consumer.close();
    }

    private static Consumer<String, String> createConsumer() {
        Properties props = new Properties();
        //write here ip address which you want to connect to
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.1:9092");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.204.9:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"fjkdsgfkjhsdjfhs");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "your_client_id777");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // Create the consumer using props.
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList("transactions"));
        return consumer;
    }
}
