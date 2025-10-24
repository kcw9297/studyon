package studyon.app.layer.domain.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.service.LectureService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final LectureService lectureService;

    @GetMapping("index.html")
    public String index() {
        return "redirect:/";
    }
    /**
     * [GET] 해당하는 과목 추천 페이지
     */
    @GetMapping
    public String viewHome(Model model) {
        return ViewUtils.returnView(model, View.HOME, "home");
    }
}
