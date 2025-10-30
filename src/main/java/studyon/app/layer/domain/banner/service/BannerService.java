package studyon.app.layer.domain.banner.service;

import org.springframework.web.multipart.MultipartFile;
import studyon.app.layer.domain.banner.BannerDTO;

import java.util.List;

/**
 * 배너 비즈니스 로직 처리
 * @version 1.0
 * @author kcw97
 */

public interface BannerService {

    /**
     * 배너 전체 조회
     * @return 전체 조회된 배너 리스트
     */
    List<BannerDTO.Read> readAll();

    /**
     * 활성 상태의 배너만 일괄 조회
     * @return 활성 상태의 배너 전체 리스트
     */
    List<BannerDTO.Read> readAllActivate();

    /**
     * 배너 제목 수정 (관리자 구별용)
     * @param idx 대상 배너 번호
     * @param title 변경 제목
     */
    void editTitle(Integer idx, String title);

    /**
     * 배너 이미지 수정 (실제로 사용자에게 보임)
     * @param idx 대상 배너 번호
     * @param bannerImageFile 변경 이미지
     */
    void editNoticeImage(Integer idx, MultipartFile bannerImageFile);

    /**
     * 배너 활성화 (사용자에게 표시)
     * @param idx 대상 배너 번호
     */
    void activate(Integer idx);

    /**
     * 배너 비활성화 (사용자에게 미표시)
     * @param idx 대상 배너 번호
     */
    void inactivate(Integer idx);

    /**
     * 배너 초기화 (이미지, 제목, 활성상태 모두 초기화)
     * @param idx 대상 배너 번호
     */
    void initialize(Integer idx);
}
