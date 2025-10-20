package studyon.app.layer.domain.lecture_review.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.layer.domain.lecture_review.LectureReview;

import java.util.List;

/**
 * 강의 리뷰 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureReviewRepository extends JpaRepository<LectureReview, Long> {
    /**
     * 특정 선생님 ID로 해당 선생님의 모든 강의 리뷰를 최신순으로 조회
     */

    @Query("SELECT r FROM LectureReview r " +  // 리뷰 엔티티를 조회 시작점으로
            "JOIN FETCH r.lecture l " +  // 리뷰와 강의를 조인(fetch join으로 lecture를 즉시 로딩)
            "JOIN FETCH l.teacher t " +  // 강의와 선생님을 조인(fetch join으로 teacher를 즉시 로딩)
            "JOIN FETCH r.member m " +  // 선생님과 멤버 조인
            "WHERE t.teacherId = :teacherId " +
            "ORDER BY r.createdAt DESC")
    List<LectureReview> findRecentReviewsByTeacherId(Long teacherId, Pageable pageable);
}
