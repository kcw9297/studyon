package studyon.app.layer.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.layer.domain.notice.Notice;

import java.util.List;
import java.util.Optional;

/**
 * 공지사항 데이터 조회 JPA Repository 클래스
 */

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("""
        SELECT n
        FROM Notice n
        LEFT JOIN FETCH n.noticeImage
    """)
    List<Notice> findAllWithFile();

    @Query("""
        SELECT n
        FROM Notice n
        LEFT JOIN FETCH n.noticeImage
        WHERE n.isActivate = :isActivate
        ORDER BY n.idx ASC
    """)
    List<Notice> findAllWithFileByIsActivate(Boolean isActivate);

    Optional<Notice> findByIdx(Integer idx);
}
