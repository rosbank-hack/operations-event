package ros.hack.operations.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ros.hack.operations.event.service.EventParser;
import ros.hack.operations.event.service.OperationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationListener implements Listener<String, String> {

    private final OperationService operationService;

    @KafkaListener(topics = "${spring.kafka.topics}")
    public void listen(ConsumerRecord<String, String> consumerRecord) {

        log.info("Входящее сообщение: {}", consumerRecord.value());

        // Оптимизация для высокой нагрузки
        if (log.isDebugEnabled()) {
            log.debug("Входящее сообщение: {}", consumerRecord.value());
        }

        EventParser.parse(consumerRecord.value())
                .ifPresentOrElse(operationService::processMessage,
                        () -> log.error("Не удалось дессериализовать объект"));
    }
}
