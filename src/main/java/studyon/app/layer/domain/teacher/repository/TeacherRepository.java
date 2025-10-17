package studyon.app.layer.domain.teacher.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.teacher.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
