package studyon.app.layer.domain.lecture_category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture_category.LectureCategory;

public interface LectureCategoryRepository extends JpaRepository<LectureCategory, Long> {
}
