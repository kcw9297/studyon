package studyon.app.layer.domain.lecture_review.service;

import studyon.app.common.enums.Subject;
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
     *
     * @param teacherId 선생님 ID
     * @param count 리스팅 개수
     * @return 리뷰 DTO 리스트
     */
    List<LectureReviewDTO.Read> readTeacherReviews(Long teacherId, int count);

    /**
     * 특정 과목의 모든 강의 리뷰를 최신순으로 조회
     *
     * @param subject 과목 enum
     * @param count 리스팅 개수
     * @return 리뷰 DTO 리스트
     */
    List<LectureReviewDTO.Read> readSubjectReviews(Subject subject, int count);

    /** 과목별 강의 리뷰 목록 조회 로직(추천 강의 화면)
     * @param count 정렬용 변수
     * @return 과목별 최근 강의 리뷰 목록
     */
    List<LectureReviewDTO.Read> readRecentLectureReviews(Subject subject, int count);

    /**
     * 선생님 최신 리뷰 조회
     * @return 해당 선생님 최신 리뷰 리스트
     */
    List<LectureReviewDTO.Read> readRecentReview(Long teacherId, int count);

    /** 강의 평점 업데이트 로직
     * @param lectureId
     * @return 평점 계산 결과
     */
    Double updateAverageRatings(Long lectureId);
}
