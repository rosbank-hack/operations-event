package operations.event.api.service.consumer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import operations.event.api.service.OperationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static operations.event.api.utils.JsonParser.parse;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationConsumer
        implements Consumer<String, String> {

    private final OperationService operationService;

    @Override
    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id")
    public void consume(@NonNull ConsumerRecord<String, String> consumerRecord) {
        log.debug(consumerRecord.toString());
        operationService.processMessage(parse(consumerRecord.value()));
    }
}
