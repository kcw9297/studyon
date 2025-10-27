package studyon.app.layer.domain.notice.service;

import org.springframework.web.multipart.MultipartFile;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.notice.NoticeDTO;

import java.util.Collection;

/**
 * 공지사항 비즈니스 로직 처리
 * @version 1.0
 * @author kcw97
 */

public interface NoticeService {

    Page.Response<NoticeDTO.Read> readPagedList(NoticeDTO.Search rq, Page.Request prq);

    NoticeDTO.Read read(Long noticeId);

    NoticeDTO.Read write(NoticeDTO.Write rq);

    void edit(NoticeDTO.Edit rq);

    void editNoticeImage(Long noticeId, MultipartFile noticeImageFile);

    void activate(Long noticeId);

    void inactivate(Long noticeId);

    void remove(Long noticeId);

    void removeAll(Collection<Long> noticeIds);
}
