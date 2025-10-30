package studyon.app.layer.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.layer.domain.notice.Notice;

import java.util.List;
import java.util.Optional;

/**
 * 공지사항 데이터 조회 JPA Repository 클래스
 * @version 1.0
 * @author kcw97
 */

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("""
        SELECT n
        FROM Notice n
        LEFT JOIN FETCH n.noticeImage
    """)
    List<Notice> findAllWithNoticeImage();

    @Query("""
        SELECT n
        FROM Notice n
        LEFT JOIN FETCH n.noticeImage
        WHERE n.isActivate = :isActivate
        ORDER BY n.idx ASC
    """)
    List<Notice> findAllWithNoticeImageByIsActivate(Boolean isActivate);

    Optional<Notice> findByIdx(Integer idx);
}
