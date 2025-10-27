package studyon.app.layer.domain.lecture_review.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture_review.LectureReview;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 수정
 * ▶ ver 1.1 (2025-10-24) : phj : 강의 정보 조회 - 강의소개 페이지
 */

/**
 * 강의 리뷰 레포지토리 인터페이스
 * @version 1.1
 * @author phj
 */

public interface LectureReviewRepository extends JpaRepository<LectureReview, Long> {
    /**
     * 특정 선생님 ID로 해당 선생님의 모든 강의 리뷰를 최신순으로 조회
     * @param teacherId 선생님 ID
     * @param pageable 정렬 조회용 변수
     * @return 선생님별 리뷰 리스트
     */
    // FETCH JOIN 사용을 통해 지연 로딩으로 인한 단점 방지
    @Query(value = "SELECT r FROM LectureReview r " +  // 리뷰 엔티티를 조회 시작점으로
            "JOIN FETCH r.lecture l " +  // 리뷰와 강의를 조인
            "JOIN FETCH l.teacher t " +  // 강의와 선생님 조인
            "JOIN FETCH r.member m " +  // 선생님과 멤버 조인
            "WHERE t.teacherId = :teacherId " +
            "ORDER BY r.createdAt DESC",
            countQuery = "SELECT COUNT(r) FROM LectureReview r JOIN r.lecture l JOIN l.teacher t WHERE t.teacherId = :teacherId")
    // countQuery == 페이징(리스팅) 오류를 막기 위해 씀
    List<LectureReview> findRecentReviewsByTeacherId(Long teacherId, Pageable pageable);

    /**
     * 과목별로 최근 강의 리뷰를 최신순으로 조회
     * @param pageable 정렬 조회용 변수
     * @return 과목별 최근 리뷰 리스트
     */

    @Query("""
        SELECT r 
        FROM LectureReview r
        JOIN FETCH r.lecture l
        JOIN FETCH l.teacher t
        JOIN FETCH r.member m
        WHERE t.subject = :subject
        ORDER BY r.createdAt DESC
    """)
    List<LectureReview> findRecentReviewsBySubject(@Param("subject") Subject subject, Pageable pageable);

    /**
     * 한 강의의 리뷰 조회(업데이트 전용)
     * @param lectureId 강의 ID
     * @return 해당 강의 리스트
     */
    List<LectureReview> findByLecture_LectureId(Long lectureId);

    /* 강의 정보 조회 - 강의소개 페이지 */
    @Query("SELECT r FROM LectureReview r JOIN FETCH r.member WHERE r.lecture.lectureId = :lectureId ORDER BY r.rating DESC")
    List<LectureReview> findByLectureIdWithMemberOrderByRatingDesc(@Param("lectureId") Long lectureId);
    Long countByLecture_LectureId(Long lectureId);

    /* 별점 퍼센트 */
    Long countByLecture_LectureIdAndRating(Long lectureId, Integer rating);

}
