package studyon.app.layer.domain.notice.service;

import org.springframework.web.multipart.MultipartFile;
import studyon.app.layer.domain.notice.NoticeDTO;

import java.util.List;

/**
 * 공지사항 비즈니스 로직 처리
 * @version 1.0
 * @author kcw97
 */

public interface NoticeService {

    /**
     * 공지사항 전체 조회
     * @return 전체 조회된 공지사항 리스트
     */
    List<NoticeDTO.Read> readAll();

    /**
     * 활성 상태의 공지만 일괄 조회
     * @return 활성 상태의 공지사항 전체 리스트
     */
    List<NoticeDTO.Read> readAllActivate();

    /**
     * 공지사항 제목 수정 (관리자 구별용)
     * @param index 대상 공지 번호
     * @param title 변경 제목
     */
    void editTitle(Integer index, String title);

    /**
     * 공지사항 이미지 수정 (실제로 사용자에게 보임)
     * @param index 대상 공지 번호
     * @param noticeImageFile 변경 이미지
     */
    void editNoticeImage(Integer index, MultipartFile noticeImageFile);

    /**
     * 공지사항 활성화 (사용자에게 표시)
     * @param index 대상 공지 번호
     */
    void activate(Integer index);

    /**
     * 공지사항 비활성화 (사용자에게 미표시)
     * @param index 대상 공지 번호
     */
    void inactivate(Integer index);

    /**
     * 공지사항 초기화 (이미지, 제목, 활성상태 모두 초기화)
     * @param index 대상 공지 번호
     */
    void initialize(Integer index);
}
