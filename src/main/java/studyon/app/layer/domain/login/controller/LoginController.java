package studyon.app.layer.domain.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.common.constant.Url;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97
 */

/**
 * 로그인 컨트롤러 클래스
 * @version 1.0
 * @author kcw97
 */

@Controller
public class LoginController {

    @GetMapping(Url.LOGIN)
    public String loginView() {
        return "page/login/login";
    }
}
