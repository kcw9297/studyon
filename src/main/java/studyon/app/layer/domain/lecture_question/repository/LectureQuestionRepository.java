package studyon.app.layer.domain.lecture_question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studyon.app.layer.domain.lecture_question.LectureQuestion;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 질문 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 * 20251029 khs97 findbylectureindex_lectureindexid 추가
 */

public interface LectureQuestionRepository extends JpaRepository<LectureQuestion, Long> {
    List<LectureQuestion> findByLecture_LectureIdAndLectureIndex_LectureIndexId(Long lectureId, Long lectureIndexId);
    @Query("""
    SELECT q
    FROM LectureQuestion q
    JOIN q.lecture l
    JOIN l.teacher t
    LEFT JOIN FETCH q.lectureAnswer a
    LEFT JOIN FETCH q.lectureIndex i
    WHERE t.teacherId = :teacherId
    ORDER BY q.createdAt DESC
""")
    List<LectureQuestion> findAllWithAnswerByTeacherId(@Param("teacherId") Long teacherId);
}
