package operations.event.api.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import operations.event.api.service.JsonParser;
import operations.event.api.service.OperationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationListener implements Listener<String, String> {

    private final OperationService operationService;

    @KafkaListener(topics = "${spring.kafka.topic}")
    public void listen(ConsumerRecord<String, String> consumerRecord) {

        if (log.isDebugEnabled()) {
            log.debug("Received record: {}", consumerRecord.value());
        }

        JsonParser jsonParser = new JsonParser();
        operationService.processMessage(jsonParser.parse(consumerRecord.value()));
    }
}
