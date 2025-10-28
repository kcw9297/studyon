package studyon.app.layer.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.validation.annotation.Title;
import studyon.app.layer.domain.notice.service.NoticeService;

/**
 * 공지사항 요청을 받기 위한 관리자용 컨트롤러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(Url.ADMIN_NOTICES_API)
@RequiredArgsConstructor
@Validated
public class AdminNoticeRestController {

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<?> readAll() {
        return RestUtils.ok(noticeService.readAll());
    }


    @PatchMapping("/{idx}/title")
    public ResponseEntity<?> editTitle(@PathVariable Integer idx,
                                       @Title(max=15) String title) {

        // [1] 공지사항 수정
        noticeService.editTitle(idx, title);

        // [2] 성공 응답 반환
        return RestUtils.ok();
    }

    @PatchMapping("/{idx}/notice_image")
    public ResponseEntity<?> editNoticeImage(@PathVariable Integer idx, MultipartFile noticeImageFile) {

        // [1] 공지사항 이미지 수정
        noticeService.editNoticeImage(idx, noticeImageFile);

        // [2] 성공 응답 반환
        return RestUtils.ok();
    }

    @PatchMapping("/{idx}/activate")
    public ResponseEntity<?> activate(@PathVariable Integer idx) {

        // [1] 공지사항 활성화 수행
        noticeService.activate(idx);

        // [2] 성공 응답 반환
        return RestUtils.ok();
    }


    @PatchMapping("/{idx}/inactivate")
    public ResponseEntity<?> inactivate(@PathVariable Integer idx) {

        // [1] 공지사항 비활성화 수행
        noticeService.inactivate(idx);

        // [2] 성공 응답 반환
        return RestUtils.ok();
    }

    @PutMapping("/{idx}/initialize")
    public ResponseEntity<?> initialize(@PathVariable Integer idx) {

        // [1] 공지사항 비활성화 수행
        noticeService.initialize(idx);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.NOTICE_OK_INITIALIZE);
    }



}
