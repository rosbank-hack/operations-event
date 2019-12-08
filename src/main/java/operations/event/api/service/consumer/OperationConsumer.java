package operations.event.api.service.consumer;

import com.github.voteva.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import operations.event.api.service.OperationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationConsumer
        implements Consumer {

    private final OperationService operationService;

    @Override
    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id")
    public void consume(@NonNull List<Operation> operations) {
        operations.forEach(operation -> {
            log.debug(operation.toString());
            operationService.processMessage(operation);
        });
    }
}
