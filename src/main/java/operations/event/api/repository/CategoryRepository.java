package operations.event.api.repository;

import operations.event.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, String> { }
