package operations.event.api.service;

import com.github.voteva.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import operations.event.api.entity.OperationEntity;
import operations.event.api.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.UUID;

import static operations.event.api.consts.Constants.DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;

    @SneakyThrows({ParseException.class})
    public void processMessage(@NonNull Operation operation) {

        OperationEntity entity = new OperationEntity();
        entity.setUserId(operation.getUserId());
        entity.setMcc(operation.getMcc());
        entity.setOperationId(UUID.fromString(operation.getOperationId()));
        entity.setService(operation.getInitiator());
        entity.setAmount(
                new BigDecimal(getFromPaymentService(operation, "amount")));
        entity.setCurrency(getFromPaymentService(operation, "currency"));
        entity.setName(getFromPaymentService(operation, "comment"));
        entity.setDate(
                new SimpleDateFormat(DATE_FORMAT)
                        .parse(getFromPaymentService(operation, "date"))
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime());
        entity.setStatus(getFromPaymentService(operation, "status"));
        entity.setExtendedStatus(getFromPaymentService(operation, "extendedStatus"));

        if (!operationRepository.existsByUserIdAndDate(entity.getUserId(), entity.getDate())) {
            operationRepository.save(entity);
        }
    }

    private String getFromPaymentService(Operation operation, String target) {
        return operation
                .getServices()
                .get("payment")
                .getResponse()
                .get(target);
    }
}
