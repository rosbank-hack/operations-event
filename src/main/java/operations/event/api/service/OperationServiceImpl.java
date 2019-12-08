package operations.event.api.service;

import com.github.voteva.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import operations.event.api.entity.OperationEntity;
import operations.event.api.repository.OperationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

import static operations.event.api.consts.Constants.DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl
        implements OperationService {

    private final OperationRepository operationRepository;

    @SneakyThrows
    public void processMessage(@NonNull Operation operation) {
        final OperationEntity operationEntity = buildOperationEntity(operation);
        if (!operationRepository.existsByServiceAndOperationId(
                operationEntity.getService(),
                operationEntity.getOperationId())) {
            operationRepository.save(operationEntity);
        }
    }

    @Nonnull
    private String getFromPaymentService(@Nonnull Operation operation, @Nonnull String target) {
        return operation
                .getServices()
                .get("PAYMENTS")
                .getResponse()
                .get(target);
    }

    @Nonnull
    @SneakyThrows
    private OperationEntity buildOperationEntity(@Nonnull Operation operation) {
        return new OperationEntity()
                .setUserId(operation.getUserId())
                .setMcc(operation.getMcc())
                .setOperationId(operation.getOperationId())
                .setService(operation.getInitiator())
                .setAmount(new BigDecimal(getFromPaymentService(operation, "amount")))
                .setCurrency(getFromPaymentService(operation, "currency"))
                .setName(getFromPaymentService(operation, "comment"))
                .setStatus(getFromPaymentService(operation, "status"))
                .setExtendedStatus(getFromPaymentService(operation, "extendedStatus"))
                .setDate(new SimpleDateFormat(DATE_FORMAT)
                        .parse(getFromPaymentService(operation, "date"))
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
    }
}
