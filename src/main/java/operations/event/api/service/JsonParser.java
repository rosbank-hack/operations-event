package operations.event.api.service;

import com.github.voteva.Operation;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JsonParser {

    @SneakyThrows({JsonSyntaxException.class})
    public Operation parse(String message) {
        log.debug(message);

        Gson gson = new Gson();
        return gson.fromJson(message, Operation.class);
    }

}
