package operations.event.api.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.voteva.Operation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public abstract class EventParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows({JsonParseException.class, IOException.class})
    public static Optional<Operation> parse(String message) {

        log.debug(message);
        return Optional.of(mapper.readValue(message, Operation.class));
    }

}
