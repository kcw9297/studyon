package studyon.app.layer.domain.lecture.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studyon.app.common.enums.Subject;
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
    /**
     * 최근 등록된 강의 (홈화면)
     * @param pageable 정렬용 변수
     * @return 최근 등록된 강의 리스트
     */
    List<Lecture> findAllByOrderByPublishDateDesc(Pageable pageable);

    /**
     * 최근 인기 강의 (홈화면) -> 총 수강 학생 수 정렬
     * @param pageable 정렬용 변수
     * @return 인기순 강의 리스트
     */
    List<Lecture> findAllByOrderByTotalStudentsDesc(Pageable pageable);

    /**
     * 특정 선생님이 담당하는 BEST 강의 조회
     * @param teacherId 선생님 ID
     * @param pageable 정렬용 변수
     * @return 해당 선생님이 등록한 강의 리스트
     */
    @Query("SELECT l FROM Lecture l WHERE l.teacher.teacherId = :teacherId ORDER BY l.totalStudents DESC")
    List<Lecture> findBestLecturesByTeacherId(@Param("teacherId") Long teacherId, Pageable pageable);


    /**
     * 특정 과목의 BEST 강의 조회
     * @param subject 과목
     * @param pageable 정렬용 변수
     * @return 해당 선생님이 등록한 최신 강의 리스트 (total_students 기준 정렬)
     */
    @Query("SELECT l FROM Lecture l WHERE l.teacher.subject = :subject ORDER BY l.totalStudents DESC")
    List<Lecture> findBestLecturesBySubject(@Param("subject") Subject subject, Pageable pageable);

    /**
     * 특정 선생님의 최근 등록된 강의 조회
     * @param teacherId 선생님 ID
     * @param pageable 정렬용 변수
     * @return 해당 선생님이 등록한 최신 강의 리스트 (publish_date 기준 정렬)
     */
    @Query("SELECT l FROM Lecture l WHERE l.teacher.teacherId = :teacherId ORDER BY l.publishDate DESC")
    List<Lecture> findRecentLecturesByTeacherId(@Param("teacherId") Long teacherId, Pageable pageable);

    /**
     * 특정 과목의 최근 등록된 강의 조회
     * @param subject 과목
     * @param pageable 정렬용 변수
     * @return 해당 선생님이 등록한 최신 강의 리스트 (publish_date 기준 정렬)
     */
    @Query("SELECT l FROM Lecture l WHERE l.teacher.subject = :subject ORDER BY l.publishDate DESC")
    List<Lecture> findRecentLecturesBySubject(@Param("subject") Subject subject, Pageable pageable);

    /* 테스트용 코드 */
    // 테스트용 데이터 정렬
    Page<Lecture> findByOrderByPublishDateDesc(Pageable pageable);

    // 강의 리뷰 정렬
    List<LectureReview> findByLectureId(Long lectureId);

}