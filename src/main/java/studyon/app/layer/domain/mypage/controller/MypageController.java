package studyon.app.layer.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    @GetMapping
    public String mypage(Model model) {
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/account.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }

    @GetMapping("/coupons")
    public String coupons(Model model) {
        model.addAttribute("bodyPage", "/WEB-INF/views/page/mypage/coupons.jsp");
        return ViewUtils.returnView(model, View.MYPAGE, "template");
    }
}