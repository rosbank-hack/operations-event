package operations.event.api.repository;

import operations.event.api.entity.OperationParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationParamsRepository extends JpaRepository<OperationParams, Integer> {

}
