package studyon.app.layer.domain.lecture_video.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import studyon.app.layer.domain.lecture_video.LectureVideo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface LectureVideoRepository extends JpaRepository<LectureVideo, Long> {

    // ✅ 특정 강의의 영상 목록을 seq 순으로 조회
    @Query("""
        SELECT v
        FROM LectureVideo v
        WHERE v.lectureIndex.lecture.lectureId = :lectureId
        ORDER BY v.seq ASC
    """)
    List<LectureVideo> findAllByLectureId(Long lectureId);

    @Query("""
        SELECT v
        FROM LectureVideo v
        JOIN FETCH v.lectureIndex i
        JOIN FETCH i.lecture l
        WHERE l.lectureId = :lectureId
        ORDER BY i.indexNumber ASC, v.seq ASC
    """)
    List<LectureVideo> findLectureVideosWithIndex(Long lectureId);

    @Query("""
    SELECT v
    FROM LectureVideo v
    WHERE v.lectureIndex.lectureIndexId = :indexId
    ORDER BY v.seq ASC
""")
    List<LectureVideo> findAllByLectureIndexId(@Param("indexId") Long indexId);
    List<LectureVideo> findAllByLectureIndex_LectureIndexId(Long indexId);

    @Query("""
    SELECT v FROM LectureVideo v
    WHERE v.lectureIndex.lectureIndexId = :indexId
    ORDER BY v.createdAt DESC
""")
    LectureVideo findTopByLectureIndexId(@Param("indexId") Long indexId);
    Optional<LectureVideo> findFirstByLectureIndex_LectureIndexIdOrderBySeqAsc(Long lectureIndexId);
    long countByLectureIndex_Lecture_LectureId(Long lectureId);

    // 영상 총 길이 업데이트
    @Query("""
    SELECT COALESCE(SUM(v.duration), 0)
    FROM LectureVideo v
    JOIN v.lectureIndex i
    WHERE i.lecture.lectureId = :lectureId
""")
    Long sumDurationByLectureId(@Param("lectureId") Long lectureId);
}

