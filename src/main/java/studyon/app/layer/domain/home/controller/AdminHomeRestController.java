package studyon.app.layer.domain.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.common.constant.URL;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.home.service.AdminHomeService;


@Slf4j
@RestController
@RequestMapping(URL.ADMIN_HOME_API) // "/api/admin/home"
@RequiredArgsConstructor
public class AdminHomeRestController {

    private final AdminHomeService adminHomeService;

    /**
     * [GET] 회원 수 조회
     */
    @GetMapping("/countAllMembers")
    public ResponseEntity<?> countAllMembers() {
        // [1] 회원 수 가져오기
        Long totalMembers = adminHomeService.readAllMemberCount();
        // [2] 성공 응답 반환
        return RestUtils.ok(totalMembers);
    }

    /**
     * [GET] 모든 강의 수 가져오기
     * @return 모든 강의 수
     */
    @GetMapping("/countAllLectures")
    public ResponseEntity<?> readAllLectureCount() {
        log.info(" GET 요청: 모든 강의 수 조회");
        Long countAll = adminHomeService.readAllLectureCount();
        return RestUtils.ok(countAll);
    }

    @GetMapping("/countNewMembers")
    public ResponseEntity<?> countNewMembers() {
        Long newMembers = adminHomeService.readTodayNewMemberCount();
        return RestUtils.ok(newMembers);
    }

    @GetMapping("/activeMembers")
    public ResponseEntity<?> countActiveMembers() {
        Long activeMembers = adminHomeService.readActiveMemberCount();
        return RestUtils.ok(activeMembers);
    }

    @GetMapping("/totalSales")
    public ResponseEntity<?> readAllSales() {
        Long totalSales = adminHomeService.readAllSales();
        return RestUtils.ok(totalSales);
    }
}
