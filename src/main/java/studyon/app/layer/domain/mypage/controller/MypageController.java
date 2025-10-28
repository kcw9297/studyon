package studyon.app.layer.domain.mypage.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture_like.LectureLike;
import studyon.app.layer.domain.lecture_like.service.LectureLikeService;

import java.util.*;

@Slf4j
@Controller
@RequestMapping(Url.MYPAGE)
@RequiredArgsConstructor
public class MypageController {

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
    public String likes(Model model, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        List<LectureLike> likeList = lectureLikeService.getLikesByMember(memberId);

        model.addAttribute("likeList", likeList);
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/likes.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    // 좋아요 삭제
    @GetMapping("/likes/delete/{lectureId}")
    public String deleteLike(@PathVariable Long lectureId, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId != null) {
            lectureLikeService.deleteLike(memberId, lectureId);
        }

        return "redirect:/mypage/likes";
    }

    @GetMapping("/lecture_management")
    public String lecture_management(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/lecture_management.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/payment")
    public String payment(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/payment.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/passwordreset")
    public String passwordreset(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/edit_password.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "password_reset");
    }
}