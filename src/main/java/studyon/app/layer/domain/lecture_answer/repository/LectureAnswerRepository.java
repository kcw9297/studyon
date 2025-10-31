package studyon.app.layer.domain.lecture_answer.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import studyon.app.layer.domain.lecture_answer.LectureAnswer;

import java.util.Optional;

/**
 * 강의 답변 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureAnswerRepository extends JpaRepository<LectureAnswer, Long> {
    @Modifying
    @Query("UPDATE LectureAnswer a SET a.content = :content WHERE a.lectureAnswerId = :answerId")
    void updateAnswerContent(@Param("answerId") Long answerId, @Param("content") String content);
}

