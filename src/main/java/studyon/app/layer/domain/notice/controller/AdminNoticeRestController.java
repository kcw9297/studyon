package studyon.app.layer.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.notice.NoticeDTO;
import studyon.app.layer.domain.notice.service.NoticeService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(Url.ADMIN_NOTICES_API)
@RequiredArgsConstructor
public class AdminNoticeRestController {

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<?> readPagedList(NoticeDTO.Search rq, Page.Request prq) {
        return RestUtils.ok(noticeService.readPagedList(rq, prq));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<?> read(@PathVariable Long noticeId) {
        return RestUtils.ok(noticeService.read(noticeId));
    }

    @PostMapping
    public ResponseEntity<?> write(NoticeDTO.Write rq) {

        // [1] 공지 작성
        NoticeDTO.Read read = noticeService.write(rq);

        // [2] 성공 응답 반환
        return RestUtils.ok(read, AppStatus.NOTICE_OK_WRITE);
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<?> edit(@PathVariable Long noticeId, NoticeDTO.Edit rq) {

        // [1] 공지사항 수정
        rq.setNoticeId(noticeId); // id 값 삽입
        noticeService.edit(rq);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.NOTICE_OK_EDIT);
    }

    @PatchMapping("/{noticeId}/notice_image")
    public ResponseEntity<?> editNoticeImage(@PathVariable Long noticeId, MultipartFile noticeImageFile) {

        // [1] 공지사항 이미지 수정
        noticeService.editNoticeImage(noticeId, noticeImageFile);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.NOTICE_OK_EDIT_IMAGE);
    }

    @PatchMapping("/{noticeId}/activate")
    public ResponseEntity<?> activate(@PathVariable Long noticeId) {

        // [1] 공지사항 활성화 수행
        noticeService.activate(noticeId);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.NOTICE_OK_ACTIVATE);
    }


    @PatchMapping("/{noticeId}/inactivate")
    public ResponseEntity<?> inactivate(@PathVariable Long noticeId) {

        // [1] 공지사항 비활성화 수행
        noticeService.inactivate(noticeId);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.NOTICE_OK_INACTIVATE);
    }




}
