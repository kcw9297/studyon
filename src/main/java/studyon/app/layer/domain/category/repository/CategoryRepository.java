package studyon.app.layer.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
