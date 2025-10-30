package studyon.app.layer.domain.banner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.banner.service.BannerService;

/**
 * 배너 요청을 받기 위한 컨트롤러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(Url.BANNERS_API)
@RequiredArgsConstructor
public class BannerRestController {

    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<?> readAllActivate() {
        return RestUtils.ok(bannerService.readAllActivate());
    }

}
