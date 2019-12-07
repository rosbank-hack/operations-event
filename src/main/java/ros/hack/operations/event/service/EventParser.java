package ros.hack.operations.event.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.voteva.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public abstract class EventParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Optional<Operation> parse(String message) {
        try {
            return Optional.of(mapper.readValue(message, Operation.class));
        } catch (IOException e) {
            log.error("Не удалось дессериалоизовать сообщение");
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
