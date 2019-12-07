package ros.hack.operations.event.repository;

import com.github.voteva.Operation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OperationRepository implements Repository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public int update(Operation operation) {
//        return jdbcTemplate.update("");
        return 0;
    }
}
