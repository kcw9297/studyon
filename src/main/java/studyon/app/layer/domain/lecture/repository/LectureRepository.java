package studyon.app.layer.domain.lecture.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.layer.domain.lecture.Lecture;

import java.util.List;
import java.util.Objects;

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

    // 최신 등록순 (publishDate 내림차순)
    List<Lecture> findAllByOrderByPublishDateDesc();

    // 최근 꺼 다섯개만.
    Page<Lecture> findTop5ByOrderByPublishDateDesc(Pageable pageable);

    /**
     * 강의별 평균 평점 조회
     * (lecture_review 테이블의 평균 rating을 계산)
     */

    @Query("""
            SELECT lr.lecture.lectureId AS lectureId,
                           ROUND(AVG(lr.rating), 2) AS avgRating
                    FROM LectureReview lr
                    GROUP BY lr.lecture.lectureId
            """)
    List<Object[]> findLectureAverageRatings();

}
