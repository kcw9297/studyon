package studyon.app.layer.domain.lecture.controller;

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
@RequestMapping("/lecture_detail")
@RequiredArgsConstructor
public class LectureDetailController {

    @GetMapping
    public String lecture_detail(Model model) {
        return ViewUtils.returnView(model, View.LECTURE, "lecture_detail");
    }
}