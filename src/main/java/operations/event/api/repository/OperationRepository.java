package operations.event.api.repository;

import operations.event.api.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;

public interface OperationRepository
        extends JpaRepository<OperationEntity, Long> {

    boolean existsByServiceAndOperationId(@Nonnull String service, @Nonnull String operationId);

    OperationEntity findByOperationId(@Nonnull String operationId);
}
