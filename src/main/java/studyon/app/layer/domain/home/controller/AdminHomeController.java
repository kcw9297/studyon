package studyon.app.layer.domain.home.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.*;
import studyon.app.common.enums.filter.LectureKeyword;
import studyon.app.common.enums.filter.LectureOnSale;
import studyon.app.common.enums.filter.LectureSort;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.Objects;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : khs97 최초 작성
 *  ▶ ver 1.1 (2025-10-25) : kcw97 프로필 제공방식 변경
 *  ▶ ver 1.2 (2025-10-26) : kcw97 위치 변경 (HOME)
 *  ▶ ver 1.3 (2025-10-29) : kcw97 불필요 반복 코드 삭제 및 강의 검색필터 제공 코드 추가
 */

/**
 * 관리자 view 컨트롤러 클래스
 * @version 1.3
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
        return ViewUtils.returnView(model, View.ADMIN, "teacher_management");
    }

    @GetMapping("/teacher_management/new")
    public String adminTeacherCreate(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.ADMIN, "teacher_register");
    }

    @GetMapping("/member_management")
    public String adminMemberManagement(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.ADMIN, "member_management");
    }

    @GetMapping("/notice_management")
    public String adminNoticeManagement(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.ADMIN, "notice_management");
    }

    @GetMapping("/banner_management")
    public String adminBannerManagement(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.ADMIN, "banner_management");
    }

    @GetMapping("/lecture_management")
    public String showLectureList(Model model, LectureDTO.Search rq, Page.Request prq) {

        // [1] 필터를 위한 데이터 삽입
        model.addAttribute("subjects", Subject.values());
        model.addAttribute("subjectDetails", SubjectDetail.values());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("filters", LectureKeyword.values());
        model.addAttribute("sorts", LectureSort.values());
        model.addAttribute("targets", LectureTarget.values());

        // [2] 관리자 전용 추가 필터 삽입
        model.addAttribute("onSales", LectureOnSale.values());
        model.addAttribute("statuses", LectureRegisterStatus.values());

        // [3] view 반환
        return ViewUtils.returnView(model, View.ADMIN, "lecture_management");
    }

    @GetMapping("/lecture_statistics")
    public String adminLectureStatistics(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.ADMIN, "lecture_statistics");
    }

    @GetMapping("/report_management")
    public String adminReportManagement(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.ADMIN, "report_management");
    }

    @GetMapping("/payment_management")
    public String adminPaymentManagement(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.ADMIN, "payment_management");
    }



}
