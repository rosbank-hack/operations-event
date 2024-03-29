package operations.event.api.service;

import com.github.voteva.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import operations.event.api.entity.OperationEntity;
import operations.event.api.repository.OperationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static operations.event.api.consts.Constants.DATE_FORMAT;
import static operations.event.api.consts.Constants.PAYMENT_SERVICE;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl
        implements OperationService {

    private final OperationRepository operationRepository;

    @SneakyThrows({EntityNotFoundException.class, NullPointerException.class})
    public void processMessage(@NonNull Operation operation) {
        final OperationEntity operationEntity = buildOperationEntity(operation);
        if (!operationRepository.existsByServiceAndOperationId(
                operationEntity.getService(),
                operationEntity.getOperationId())) {
            operationRepository.save(operationEntity);
        }
        OperationEntity entity = operationRepository
                .findByOperationId(operation.getOperationId());

        Map<String, Map<String, String>> extra = new HashMap<>();
        for (Map.Entry<String, com.github.voteva.Service> entry : operation.getServices().entrySet()) {
            extra.put(entry.getKey(), entry.getValue().getResponse());
        }
        if (extra.get("BONUSES").isEmpty()) {
            Map<String, String> bonuses = new HashMap<>();
            bonuses.put("cashback", String.valueOf(Math.round(Math.random() * 100 + 23)));
            extra.put("BONUSES", bonuses);
        }
        entity.setExtra(extra.toString());
        operationRepository.save(entity);
    }

    @Nonnull
    private String getFromService(@Nonnull Operation operation, @Nonnull String service, @Nonnull String target) {
        return operation
                .getServices()
                .get(service)
                .getResponse()
                .get(target);
    }


    @Nonnull
    @SneakyThrows(ParseException.class)
    private OperationEntity buildOperationEntity(@Nonnull Operation operation) {
        return new OperationEntity()
                .setUserId(operation.getUserId())
                .setMcc(operation.getMcc())
                .setOperationId(operation.getOperationId())
                .setService(operation.getInitiator())
                .setAmount(new BigDecimal(getFromService(operation, PAYMENT_SERVICE, "amount")))
                .setCurrency(getFromService(operation, PAYMENT_SERVICE, "currency"))
                .setName(getFromService(operation, PAYMENT_SERVICE, "comment"))
                .setStatus(getFromService(operation, PAYMENT_SERVICE, "status"))
                .setExtendedStatus(getFromService(operation, PAYMENT_SERVICE, "extendedStatus"))
                .setDate(new SimpleDateFormat(DATE_FORMAT)
                        .parse(getFromService(operation, PAYMENT_SERVICE, "date"))
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
    }
}
