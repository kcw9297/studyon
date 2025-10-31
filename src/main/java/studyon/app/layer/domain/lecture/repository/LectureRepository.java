package studyon.app.layer.domain.lecture.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studyon.app.common.enums.LectureRegisterStatus;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.teacher.Teacher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-22) : khj00 : 지연 로딩 방지 @Query 추가
 *  ▶ ver 1.2 (2025-10-23) : phj : 강의 정보 조회 - 강의소개 페이지
 *  ▶ ver 1.3 (2025-10-28) : khj00 : 강의 등록 상태 분류 추가
 */

/**
 * 강의 레포지토리 인터페이스
 * @version 1.3
 * @author phj
 */

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    /**
     * 최근 등록된 강의 (홈화면)
     * @param status 강의 등록 상태
     * @param pageable 정렬용 변수
     * @return 최근 등록된 강의 리스트
     */
    @Query("""
    SELECT l FROM Lecture l
    JOIN FETCH l.teacher t
    JOIN FETCH t.member m
    WHERE l.lectureRegisterStatus = :status
    ORDER BY l.publishDate DESC
    """) // fetch join을 통해 다중 쿼리 생성 방지
    List<Lecture> findAllByOrderByPublishDateDesc(Pageable pageable,
                                                  @Param("status") LectureRegisterStatus status);

    /**
     * 최근 인기 강의 (홈화면) -> 총 수강 학생 수 정렬
     * @param status 강의 등록 상태
     * @param pageable 정렬용 변수
     * @return 인기순 강의 리스트
     */
    @Query("""
    SELECT l FROM Lecture l
    JOIN FETCH l.teacher t
    JOIN FETCH t.member m
    WHERE l.lectureRegisterStatus = :status
    ORDER BY l.totalStudents DESC
    """)
    List<Lecture> findAllByOrderByTotalStudentsDesc(Pageable pageable,
                                                    @Param("status") LectureRegisterStatus status);

    /**
     * 특정 선생님이 담당하는 BEST 강의 조회 (수강생 수 기준)
     * @param teacherId 선생님 ID
     * @param status 강의 등록 상태
     * @param pageable 정렬용 변수
     * @return 해당 선생님이 등록한 강의 리스트
     */
    @Query("""
        SELECT l FROM Lecture l
        JOIN FETCH l.teacher t
        JOIN FETCH t.member m
        WHERE t.teacherId = :teacherId
            AND l.lectureRegisterStatus = :status
        ORDER BY l.totalStudents DESC
        """)
    List<Lecture> findBestLecturesByTeacherId(@Param("teacherId") Long teacherId,
                                              @Param("status") LectureRegisterStatus status,
                                              Pageable pageable);

    /**
     * 특정 과목의 BEST 강의 조회 (수강생 수 기준)
     * @param subject 과목
     * @param pageable 정렬용 변수
     * @param status 강의 등록 상태
     * @return 해당 선생님이 등록한 최신 강의 리스트 (total_students 기준 정렬)
     */
    @Query("""
        SELECT l FROM Lecture l
        JOIN FETCH l.teacher t
        JOIN FETCH t.member m
        WHERE l.subject = :subject
            AND l.lectureRegisterStatus = :status
        ORDER BY l.totalStudents DESC
        """)
    List<Lecture> findBestLecturesBySubject(@Param("subject") Subject subject,
                                            @Param("status") LectureRegisterStatus status,
                                            Pageable pageable);

    /**
     * 특정 선생님의 최근 등록된 강의 조회
     * @param teacherId 선생님 ID
     * @param pageable 정렬용 변수
     * @return 해당 선생님이 등록한 최신 강의 리스트 (publish_date 기준 정렬)
     */
    @Query("""
        SELECT l FROM Lecture l
        JOIN FETCH l.teacher t
        JOIN FETCH t.member m
        WHERE t.teacherId = :teacherId
            AND l.lectureRegisterStatus = :status
        ORDER BY l.publishDate DESC
        """)
    List<Lecture> findRecentLecturesByTeacherId(@Param("teacherId") Long teacherId,
                                                @Param("status") LectureRegisterStatus status,
                                                Pageable pageable);

    /**
     * 특정 과목의 최근 등록된 강의 조회
     * @param subject 과목
     * @param pageable 정렬용 변수
     * @return 해당 선생님이 등록한 최신 강의 리스트 (publish_date 기준 정렬)
     */
    @Query("""
        SELECT l FROM Lecture l
        JOIN FETCH l.teacher t
        JOIN FETCH t.member m
        WHERE l.subject = :subject
            AND l.lectureRegisterStatus = :status
        ORDER BY l.publishDate DESC
        """)
    List<Lecture> findRecentLecturesBySubject(@Param("subject") Subject subject,
                                              @Param("status") LectureRegisterStatus status,
                                              Pageable pageable);

    /* 테스트용 코드 */
    // 테스트용 데이터 정렬
    Page<Lecture> findByOrderByPublishDateDesc(Pageable pageable);

    /* 강의 정보 조회 - 강의소개 페이지 */
    @Query("""
        SELECT l FROM Lecture l
        LEFT JOIN FETCH l.teacher t
        LEFT JOIN FETCH t.member m
        LEFT JOIN FETCH l.thumbnailFile
        WHERE l.lectureId = :id
        """)
    Optional<Lecture> findWithTeacherById(@Param("id") Long lectureId);

    List<Lecture> findByTeacherAndLectureRegisterStatus(Teacher teacher, LectureRegisterStatus status);
    Long countByTeacher_TeacherId(Long teacherId);

    Optional<Lecture> findByLectureIdAndOnSale(Long lectureId, Boolean onSale);

    boolean existsByLectureIdAndOnSale(Long lectureId, Boolean onSale);

    @Query("""
        SELECT l
        FROM Lecture l
        LEFT JOIN FETCH l.thumbnailFile
        LEFT JOIN FETCH l.teacher t
        LEFT JOIN FETCH t.member m
        WHERE l.lectureId = :lectureId AND l.onSale = :onSale
        """)
    Optional<Lecture> findWithThumbnailFileByLectureIdAndOnSale(Long lectureId, Boolean onSale);

    /* 알고리즘용 Best 리스트 */
    @Query("""
        SELECT l FROM Lecture l
        JOIN FETCH l.teacher t
        JOIN FETCH t.member m
        LEFT JOIN FETCH l.thumbnailFile tf
        LEFT JOIN FETCH t.member.profileImage pi
        WHERE l.subject = :subject
          AND l.lectureRegisterStatus = :status
        ORDER BY l.totalStudents DESC
    """)
    List<Lecture> findBestLecturesBySubjectAlgorithm(@Param("subject") Subject subject,
                                            @Param("status") LectureRegisterStatus status,
                                            Pageable pageable);


    @Query("""
        SELECT l FROM Lecture l
        JOIN FETCH l.teacher t
        JOIN FETCH t.member m
        LEFT JOIN FETCH l.thumbnailFile tf
        LEFT JOIN FETCH t.member.profileImage pi
        WHERE t.teacherId = :teacherId
            AND l.lectureRegisterStatus = :status
        ORDER BY l.totalStudents DESC
        """)
    List<Lecture> findBestLecturesByTeacherAlgorithm(@Param("teacherId") Long teacherId,
                                                              @Param("status") LectureRegisterStatus status,
                                                              Pageable pageable);


    Optional<Lecture> findAllFetchByLectureIdAndOnSale(Long lectureId, Boolean onSale);



    /**
     * 등록 상태별 강의 수 조회
     * REGISTERED / PENDING / REJECTED / IN_PROGRESS 등 상태 분포
     */
    @Query("SELECT l.lectureRegisterStatus AS status, COUNT(l) AS cnt FROM Lecture l GROUP BY l.lectureRegisterStatus")
    List<Map<String, Object>> findLectureCountByStatus();

    /**
     * 과목별 강의 수 조회
     * 과목(Subject) 기준으로 등록된 강의 개수를 계산
     */
    @Query("SELECT l.subject AS subject, COUNT(l) AS cnt FROM Lecture l GROUP BY l.subject")
    List<Map<String, Object>> findLectureCountBySubject();


    /**
     * 난이도별 강의 수 조회
     * 난이도 기준으로 등록된 강의 개수를 계산
     */
    @Query("""
        SELECT l.difficulty AS difficulty, COUNT(l) AS cnt
        FROM Lecture l
        GROUP BY l.difficulty
    """)
    List<Map<String, Object>> findLectureCountByDifficulty();
}