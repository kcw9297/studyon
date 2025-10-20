package studyon.app.layer.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.common.constant.URL;

@Controller
public class LoginController {

    @GetMapping(URL.LOGIN)
    public String loginView() {
        return "page/login/login";
    }
}
