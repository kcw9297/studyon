package studyon.app.layer.domain.mypage.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture_like.LectureLike;
import studyon.app.layer.domain.lecture_like.service.LectureLikeService;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.repository.PaymentRepository;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Controller
@RequestMapping(Url.MYPAGE)
@RequiredArgsConstructor
public class MypageController {
    private final PaymentRepository paymentRepository;
    private final LectureLikeService lectureLikeService;

    @GetMapping
    public String mypage(Model model) {
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/account")
    public String readAccount(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/account.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/likes")
    public String likes(@RequestParam(value = "subject", required = false, defaultValue = "all") String subject,
                        Model model, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        List<LectureLike> likeList = lectureLikeService.getLikesByMemberAndSubject(memberId, subject);

        model.addAttribute("likeList", likeList);
        model.addAttribute("selectedSubject", subject.toLowerCase());
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/likes.jsp");

        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    // 좋아요 삭제
    @GetMapping("/likes/delete/{lectureId}")
    public String deleteLike(
            @PathVariable Long lectureId,
            @RequestParam(value = "subject", required = false) String subject,
            HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId != null) {lectureLikeService.deleteLike(memberId, lectureId);}

        if (subject != null && !subject.isEmpty()) {return "redirect:/mypage/likes?subject=" + subject;}
        return "redirect:/mypage/likes";
    }


    @GetMapping("/lecture_management")
    public String lecture_management(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/lecture_management.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/payments")
    public String payments(
            @RequestParam(value="since", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate since,
            @RequestParam(value="until", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate until,
            Model model, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        // 기본값: 이번 달 1일 ~ 말일
        LocalDate now = LocalDate.now();
        if (since == null) since = now.withDayOfMonth(1);
        if (until == null) until = now.withDayOfMonth(now.lengthOfMonth());

        // 내역 조회
        List<Payment> paymentList =
                paymentRepository.findWithMemberAndLectureByMemberIdAndDateRange(memberId, since.atStartOfDay(), until.atTime(23,59,59));

        model.addAttribute("paymentList", paymentList);
        model.addAttribute("sinceDate", since);
        model.addAttribute("untilDate", until);
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/payments.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/passwordreset")
    public String passwordreset(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/edit_password.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "password_reset");
    }
}