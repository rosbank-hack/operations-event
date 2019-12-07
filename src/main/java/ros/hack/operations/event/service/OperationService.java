package ros.hack.operations.event.service;

import com.github.voteva.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ros.hack.operations.event.repository.OperationRepository;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;

    public void processMessage(@NonNull Operation operation) {
        operationRepository.update(operation);
    }
}
