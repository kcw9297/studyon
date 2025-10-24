package studyon.app.layer.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.enums.View;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.ViewUtils;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final CacheManager cacheManager;

    @GetMapping
    public String mypage(Model model) {
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/account.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/likes")
    public String likes(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/likes.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
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
}