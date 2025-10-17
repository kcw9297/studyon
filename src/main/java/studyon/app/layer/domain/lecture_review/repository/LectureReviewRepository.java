package studyon.app.layer.domain.lecture_review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;

import java.util.List;
import java.util.Optional;

/**
 * 강의 리뷰 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureReviewRepository extends JpaRepository<LectureReview, Long> {
    /**
     * 특정 선생님 ID로 해당 선생님의 모든 강의 리뷰를 최신순으로 조회
     */

    @Query("SELECT r FROM LectureReview r " +
            "JOIN r.lecture l " +
            "WHERE l.teacher.teacherId = :teacherId " +
            "ORDER BY r.createdAt DESC")
    List<LectureReview> findByTeacherIdOrderByCreatedAtDesc(Long teacherId);
}
