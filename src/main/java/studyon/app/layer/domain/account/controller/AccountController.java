package studyon.app.layer.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;

/**
 * 로그인, 회원가입 등 회원 계정의 기초가 되는 컨트롤러 클래스
 * <br>다른 컨트롤러와 달리, @RequestMapping Base Url 설정하지 않음
 * @version 1.0
 * @author kcw97
 */

@Service
@Controller
@RequiredArgsConstructor
public class AccountController {

    @GetMapping(Url.LOGIN)
    public String login(Model model) {
        return "page/account/login";
    }

    @GetMapping(Url.JOIN)
    public String join(Model model) {
        return "page/account/join";
    }

    @GetMapping(Url.JOIN_MAIL)
    public String joinMail(Model model) {
        return "page/account/join_mail";
    }
}
