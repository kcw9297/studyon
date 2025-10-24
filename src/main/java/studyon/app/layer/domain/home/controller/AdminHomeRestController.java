package studyon.app.layer.domain.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.common.constant.Url;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.home.service.AdminHomeService;


@Slf4j
@RestController
@RequestMapping(Url.ADMIN_HOME_API) // "/api/admin/home"
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
        return RestUtils.ok(totalMembers, AppStatus.OK);
    }

    /**
     * [GET] 모든 강의 수 가져오기
     * @return 모든 강의 수
     */
    @GetMapping("/countAllLectures")
    public ResponseEntity<?> readAllLectureCount() {
        log.info(" GET 요청: 모든 강의 수 조회");
        Long countAll = adminHomeService.readAllLectureCount();
        return RestUtils.ok(countAll, AppStatus.OK);
    }

    @GetMapping("/countNewMembers")
    public ResponseEntity<?> countNewMembers() {
        Long newMembers = adminHomeService.readTodayNewMemberCount();
        return RestUtils.ok(newMembers, AppStatus.OK);
    }

    @GetMapping("/activeMembers")
    public ResponseEntity<?> countActiveMembers() {
        Long activeMembers = adminHomeService.readActiveMemberCount();
        return RestUtils.ok(activeMembers, AppStatus.OK);
    }

    @GetMapping("/totalSales")
    public ResponseEntity<?> readMonthSales() {
        Long totalSales = adminHomeService.readMonthSales();
        return RestUtils.ok(totalSales, AppStatus.OK);
    }
}
