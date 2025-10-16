package studyon.app.layer.domain.lecture_review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture_review.LectureReview;

/**
 * 강의 리뷰 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureReviewRepository extends JpaRepository<LectureReview, Long> {

}
