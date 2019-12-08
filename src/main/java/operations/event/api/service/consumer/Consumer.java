package operations.event.api.service.consumer;

import com.github.voteva.Operation;

import java.util.List;

public interface Consumer {
    void consume(List<Operation> operations);
}
