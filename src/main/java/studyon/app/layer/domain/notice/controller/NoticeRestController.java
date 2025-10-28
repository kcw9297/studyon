package studyon.app.layer.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.notice.service.NoticeService;

/**
 * 공지사항 요청을 받기 위한 컨트롤러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(Url.NOTICES_API)
@RequiredArgsConstructor
public class NoticeRestController {

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<?> readAllActivate() {
        return RestUtils.ok(noticeService.readAllActivate());
    }

}
