package studyon.app.layer.domain.lecture_answer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture_answer.LectureAnswer;

/**
 * 강의 답변 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureAnswerRepository extends JpaRepository<LectureAnswer, Long> {

}
