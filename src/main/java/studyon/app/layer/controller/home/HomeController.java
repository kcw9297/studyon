package studyon.app.layer.controller.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("index.html")
    public String index() {
        return "redirect:/";
    }

    @GetMapping
    public String home(Model model) {
        return ViewUtils.returnView(model, View.HOME, "home");
    }
}
