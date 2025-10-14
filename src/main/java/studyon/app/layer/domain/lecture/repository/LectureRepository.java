package studyon.app.layer.domain.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
