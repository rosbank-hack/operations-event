package ros.hack.operations.event.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface Listener<K, V> {
    void listen(ConsumerRecord<K, V> consumerRecord);
}
