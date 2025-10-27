package studyon.app.layer.domain.lecture_index.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture_index.LectureIndex;

import java.util.List;

/**
 * 강의 목차 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureIndexRepository extends JpaRepository<LectureIndex, Long> {
    List<LectureIndex> findByLectureOrderByIndexNumberAsc(Lecture lecture);
    List<LectureIndex> findByLecture_LectureIdOrderByIndexNumberAsc(Long lectureId);
}
