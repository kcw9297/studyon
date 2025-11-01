package studyon.app.layer.domain.mypage.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture_like.LectureLike;
import studyon.app.layer.domain.lecture_like.LectureLikeDTO;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.mypage.service.MypageService;
import studyon.app.layer.domain.payment.PaymentDTO;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(Url.MYPAGE)
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

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
        MemberProfile profile = SessionUtils.getProfile(session);
        Long memberId = profile.getMemberId();
        List<LectureLikeDTO.Read> likeList = mypageService.getLikesByMemberAndSubject(memberId, subject);

        System.out.println("💚 LikeList Size = " + likeList.size());
        likeList.forEach(like ->
                System.out.println("▶ " + like.getLecture().getTitle() + " / " + like.getLecture().getLectureId())
        );


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
        if (memberId != null) {mypageService.deleteLike(memberId, lectureId);}

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
        List<PaymentDTO.Read> paymentList = mypageService.getPaymentsByMemberAndDate(memberId, since, until);

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