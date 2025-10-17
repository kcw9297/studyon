package studyon.app.layer.controller.teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.layer.base.utils.ViewUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TeacherController {

    @GetMapping("/teacher/lecture/register")
    public String loginView(Model model) {
        return ViewUtils.returnView(model, "layer/teacher/management/lecture_register");
    }
}
