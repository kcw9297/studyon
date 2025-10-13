package studyon.app.base.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login.do")
    public String loginView() {
        return "login/login";
    }
}
