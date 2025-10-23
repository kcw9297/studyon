package studyon.app.layer.domain.support;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.common.enums.View;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.member.MemberProfile;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminViewController {

    private final CacheManager cacheManager;

    /**
     * ✅ 관리자 메인 페이지
     * URL: /admin
     */
    @GetMapping("/admin/main")
    public String adminMain(Model model, HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 관리자 접근 → redirect:/login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        log.info("✅ 관리자 페이지 접근: memberId={}, role={}", memberId, profile.getRole());
        return ViewUtils.returnView(model, View.ADMIN, "admin");
    }

    @GetMapping("/admin/teacher_management")
    public String adminTeacherManagement(Model model, HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 관리자 접근 → redirect:/login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        return ViewUtils.returnView(model, View.ADMIN, "teacher_management");
    }

    @GetMapping("/admin/member_management")
    public String adminMemberManagement(Model model, HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 관리자 접근 → redirect:/login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        return ViewUtils.returnView(model, View.ADMIN, "member_management");
    }

    @GetMapping("/admin/notice_management")
    public String adminNoticeManagement(Model model, HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 관리자 접근 → redirect:/login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        return ViewUtils.returnView(model, View.ADMIN, "notice_management");
    }

    @GetMapping("/admin/banner_management")
    public String adminBannerManagement(Model model, HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 관리자 접근 → redirect:/login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        return ViewUtils.returnView(model, View.ADMIN, "banner_management");
    }

    @GetMapping("/admin/lecture_management")
    public String adminLectureManagement(Model model, HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 관리자 접근 → redirect:/login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        return ViewUtils.returnView(model, View.ADMIN, "leture_management"); // 오타 그대로 있으면 leture로
    }

    @GetMapping("/admin/lecture_statistics")
    public String adminLectureStatistics(Model model, HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 관리자 접근 → redirect:/login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        return ViewUtils.returnView(model, View.ADMIN, "lecture_statistics");
    }

    @GetMapping("/admin/report_management")
    public String adminReportManagement(Model model, HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 관리자 접근 → redirect:/login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        return ViewUtils.returnView(model, View.ADMIN, "report_management");
    }


}
