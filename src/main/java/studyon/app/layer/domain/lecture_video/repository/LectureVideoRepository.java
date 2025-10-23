package studyon.app.layer.domain.lecture_video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import studyon.app.layer.domain.lecture_video.LectureVideo;

import java.util.List;

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
}
