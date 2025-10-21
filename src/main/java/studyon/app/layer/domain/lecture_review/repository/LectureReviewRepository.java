package studyon.app.layer.domain.lecture_review.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 수정
 */

/**
 * 강의 리뷰 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureReviewRepository extends JpaRepository<LectureReview, Long> {
    /**
     * 특정 선생님 ID로 해당 선생님의 모든 강의 리뷰를 최신순으로 조회
     * @param teacherId 선생님 ID
     * @param pageable 정렬 조회용 변수
     */
    // @WHERE절 과다 ? 바인딩으로 인한 DTO 객체 반환 처리
    @Query("SELECT r FROM LectureReview r " +  // 리뷰 엔티티를 조회 시작점으로
            "JOIN r.lecture l " +  // 리뷰와 강의를 조인
            "JOIN l.teacher t " +  // 강의와 선생님 조인
            "JOIN r.member m " +  // 선생님과 멤버 조인
            "WHERE t.teacherId = :teacherId " +
            "ORDER BY r.createdAt DESC")
    List<LectureReview> findRecentReviewsByTeacherId(Long teacherId, Pageable pageable);

    /**
     * 과목별로 모든 강의 리뷰를 최신순으로 조회
     * @param pageable 정렬 조회용 변수
     */

    @Query("SELECT r FROM LectureReview r WHERE r.lecture.teacher.subject = :subject ORDER BY r.createdAt DESC")
    List<LectureReview> findRecentReviewsBySubject(@Param("subject") Subject subject, Pageable pageable);
}
