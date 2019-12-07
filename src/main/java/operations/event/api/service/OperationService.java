package operations.event.api.service;

import com.github.voteva.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import operations.event.api.entity.OperationEntity;
import operations.event.api.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;

    public void processMessage(@NonNull Operation operation) {

        OperationEntity entity = new OperationEntity();
        entity.setUserId(operation.getUserId());
        entity.setMcc(operation.getMcc());
        entity.setOperationId(UUID.fromString(operation.getOperationId()));
        //todo


        operationRepository.save(operation);
    }
}
