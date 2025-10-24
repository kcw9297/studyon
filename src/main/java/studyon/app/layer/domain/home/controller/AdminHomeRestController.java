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
        return RestUtils.ok(Rest.Message.of("총 회원 수 불러오기 성공!"), totalMembers);
    }

    /**
     * [GET] 모든 강의 수 가져오기
     * @return 모든 강의 수
     */
    @GetMapping("/countAllLectures")
    public ResponseEntity<?> readAllLectureCount() {
        log.info(" GET 요청: 모든 강의 수 조회");
        Long countAll = adminHomeService.readAllLectureCount();
        return RestUtils.ok(Rest.Message.of("모든 강의 수를 불러왔습니다.", countAll.toString()), countAll);
    }

    @GetMapping("/countNewMembers")
    public ResponseEntity<?> countNewMembers() {
        Long newMembers = adminHomeService.readTodayNewMemberCount();
        return RestUtils.ok(Rest.Message.of("오늘 신규 회원 수 불러오기 성공!"), newMembers);
    }

    @GetMapping("/activeMembers")
    public ResponseEntity<?> countActiveMembers() {
        Long activeMembers = adminHomeService.readActiveMemberCount();
        return RestUtils.ok(Rest.Message.of("활성 사용자 수(최근 7일 로그인) 불러오기 성공!"), activeMembers);
    }

    @GetMapping("/totalSales")
    public ResponseEntity<?> readAllSales() {
        Long totalSales = adminHomeService.readAllSales();
        return RestUtils.ok(Rest.Message.of("이번 달 총 매출액 불러오기 성공!"), totalSales);
    }
}
