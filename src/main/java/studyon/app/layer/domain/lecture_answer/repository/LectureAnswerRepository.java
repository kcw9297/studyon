package studyon.app.layer.domain.lecture_answer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture_answer.LectureAnswer;

public interface LectureAnswerRepository extends JpaRepository<LectureAnswer, Long> {

}
