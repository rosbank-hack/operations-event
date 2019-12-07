package operations.event.api.repository;

import operations.event.api.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Long> {

    Boolean existsByUserIdAndDate(String userId, LocalDateTime date);
}
