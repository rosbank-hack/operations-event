package operations.event.api.service;

import com.github.voteva.Operation;
import lombok.NonNull;

public interface OperationService {
    void processMessage(@NonNull Operation operation);
}
