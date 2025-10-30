package studyon.app.layer.domain.home.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.Objects;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : khs97 최초 작성
 *  ▶ ver 1.1 (2025-10-25) : kcw97 프로필 제공방식 변경
 *  ▶ ver 1.2 (2025-10-26) : kcw97 위치 변경 (HOME)
 */

/**
 * 관리자 view 컨트롤러 클래스
 * @version 1.2
 * @author khs97
 */

@Slf4j
@Controller
@RequestMapping(Url.ADMIN) // "/admin"
@RequiredArgsConstructor
public class AdminHomeController {


    /**
     * ✅ 관리자 메인 페이지
     * URL: /admin
     */
    @GetMapping
    public String showAdminHome(Model model) {
        return ViewUtils.returnView(model, View.ADMIN, "admin");
    }

    @GetMapping("/teacher_management")
    public String adminTeacherManagement(Model model, HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // 프로필이 없으면 홈으로
        if (Objects.isNull(profile)) return "redirect:%s".formatted(Url.INDEX);

        // [2] view 반환
        return ViewUtils.returnView(model, View.ADMIN, "teacher_management");
    }

    @GetMapping("/teacher_management/new")
    public String adminTeacherCreate(Model model, HttpSession session) {

        return ViewUtils.returnView(model, View.ADMIN, "teacher_register");
    }

    @GetMapping("/member_management")
    public String adminMemberManagement(Model model, HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // 프로필이 없으면 홈으로
        if (Objects.isNull(profile)) return "redirect:%s".formatted(Url.INDEX);

        // [2] view 반환
        return ViewUtils.returnView(model, View.ADMIN, "member_management");
    }

    @GetMapping("/notice_management")
    public String adminNoticeManagement(Model model, HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // 프로필이 없으면 홈으로
        if (Objects.isNull(profile)) return "redirect:%s".formatted(Url.INDEX);

        // [2] view 반환
        return ViewUtils.returnView(model, View.ADMIN, "notice_management");
    }

    @GetMapping("/banner_management")
    public String adminBannerManagement(Model model, HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // 프로필이 없으면 홈으로
        if (Objects.isNull(profile)) return "redirect:%s".formatted(Url.INDEX);

        // [2] view 반환
        return ViewUtils.returnView(model, View.ADMIN, "banner_management");
    }

    @GetMapping("/lecture_management")
    public String adminLectureManagement(Model model, HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // 프로필이 없으면 홈으로
        if (Objects.isNull(profile)) return "redirect:%s".formatted(Url.INDEX);

        // [2] view 반환
        return ViewUtils.returnView(model, View.ADMIN, "leture_management"); // 오타 그대로 있으면 leture로
    }

    @GetMapping("/lecture_statistics")
    public String adminLectureStatistics(Model model, HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // 프로필이 없으면 홈으로
        if (Objects.isNull(profile)) return "redirect:%s".formatted(Url.INDEX);

        // [2] view 반환
        return ViewUtils.returnView(model, View.ADMIN, "lecture_statistics");
    }

    @GetMapping("/report_management")
    public String adminReportManagement(Model model, HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // 프로필이 없으면 홈으로
        if (Objects.isNull(profile)) return "redirect:%s".formatted(Url.INDEX);

        // [2] view 반환
        return ViewUtils.returnView(model, View.ADMIN, "report_management");
    }

    @GetMapping("/payment_management")
    public String adminPaymentManagement(Model model, HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // 프로필이 없으면 홈으로
        if (Objects.isNull(profile)) return "redirect:%s".formatted(Url.INDEX);

        // [2] view 반환
        return ViewUtils.returnView(model, View.ADMIN, "payment_management"); // 오타 그대로 있으면 leture로
    }
}
