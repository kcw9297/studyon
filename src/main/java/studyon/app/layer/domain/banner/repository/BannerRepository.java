package studyon.app.layer.domain.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.layer.domain.banner.Banner;

import java.util.List;
import java.util.Optional;

/**
 * 배너 데이터 조회 JPA Repository 클래스
 * @version 1.0
 * @author kcw97
 */

public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Query("""
        SELECT b
        FROM Banner b
        LEFT JOIN FETCH b.bannerImage
    """)
    List<Banner> findAllWithBannerImage();

    @Query("""
        SELECT b
        FROM Banner b
        LEFT JOIN FETCH b.bannerImage
        WHERE b.isActivate = :isActivate
        ORDER BY b.idx ASC
    """)
    List<Banner> findAllWithBannerImageByIsActivate(Boolean isActivate);

    Optional<Banner> findByIdx(Integer idx);
}
