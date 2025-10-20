package studyon.app.layer.domain.lecture_review.service;

import org.springframework.data.domain.Pageable;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;

import java.util.List;
/**
 * 강의 리뷰 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureReviewService {
    /**
     * 특정 선생님의 모든 강의 리뷰를 최신순으로 조회
     * @param teacherId 선생님 ID
     * @return 리뷰 DTO 리스트
     */
    List<LectureReviewDTO.Read> getReviewsByTeacherId(Long teacherId, Pageable pageable);
}
