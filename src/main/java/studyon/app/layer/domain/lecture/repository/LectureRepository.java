package studyon.app.layer.domain.lecture.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture.Lecture;

import java.util.List;

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


}
