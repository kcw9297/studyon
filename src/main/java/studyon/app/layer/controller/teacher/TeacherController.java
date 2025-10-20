package studyon.app.layer.controller.teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Subject;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.repository.TeacherService;

import java.util.List;


@RequestMapping(URL.TEACHER)
@Slf4j
@Controller
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;


}
