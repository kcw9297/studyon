package studyon.app.layer.domain.lecture.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture_review.LectureReview;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * 강의 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    /* 테스트용 코드 */
    // 최신 등록순 (publishDate 내림차순)
    List<Lecture> findAllByOrderByPublishDateDesc();

    // 테스트용 데이터 정렬
    Page<Lecture> findByOrderByPublishDateDesc(Pageable pageable);

    // 강의 리뷰 정렬
    List<LectureReview> findByLectureId(Long lectureId);

    /**
     * 특정 선생님이 담당하는 BEST 강의 5개 정렬
     * @param teacherId 선생님 ID
     * @param pageable 정렬용 변수
     * @return 해당 선생님이 등록한 강의 리스트
     */
    @Query("SELECT l FROM Lecture l WHERE l.teacher.teacherId = :teacherId ORDER BY l.totalStudents DESC")
    List<Lecture> findBestLectures(@Param("teacherId") Long teacherId, Pageable pageable);

    /**
     * 특정 선생님의 최근 등록된 강의 5개 정렬
     * @param teacherId 선생님 ID
     * @param pageable 정렬용 변수
     * @return 해당 선생님이 등록한 최신 강의 리스트 (publish_date 기준 정렬)
     */
    @Query("SELECT l FROM Lecture l WHERE l.teacher.teacherId = :teacherId ORDER BY l.publishDate DESC")
    List<Lecture> findRecentLectures(@Param("teacherId") Long teacherId, Pageable pageable);
}