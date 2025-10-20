package studyon.app.layer.domain.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.teacher.Teacher;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 */

/**
 * 선생님 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // [1] 과목별로 선생님 불러오는 메소드
    List<Teacher> findBySubject(Subject subject);
}
