package operations.event.api.repository;

import operations.event.api.entity.OperationParams;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationParamsRepository
        extends JpaRepository<OperationParams, Integer> { }
