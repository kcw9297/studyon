package studyon.app.layer.domain.lecture_question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture_question.LectureQuestion;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 질문 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureQuestionRepository extends JpaRepository<LectureQuestion, Long> {
}
