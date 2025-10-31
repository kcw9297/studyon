package studyon.app.layer.domain.lecture_answer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture_answer.LectureAnswer;

import java.util.Optional;

/**
 * 강의 답변 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureAnswerRepository extends JpaRepository<LectureAnswer, Long> {

    // ✅ 특정 질문(lectureQuestionId)에 대한 첫 번째 답변 조회
    Optional<LectureAnswer> findFirstByLectureQuestion_LectureQuestionId(Long lectureQuestionId);
}

