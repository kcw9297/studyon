package studyon.app.layer.controller.login;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.common.constant.URLConst;

@RestController
@RequestMapping(URLConst.URL_API)
@RequiredArgsConstructor
public class LoginRestController {

    @GetMapping("/login.do")
    public String loginView() {
        return "login/login";
    }
}
