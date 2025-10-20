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

    // [1] 강의 생성 뷰
    @GetMapping(URL.LECTURE + URL.REGISTER)
    public String loginView(Model model) {
        return ViewUtils.returnView(model, , "layer/teacher/management/lecture_register");
    }


    // [2] 과목별 선생님 목록 뷰
    @GetMapping(URL.FIND + "/{subject}")
    public String teacherListView(@PathVariable Subject subject, Model model) {
        // [1] 과목별 데이터 조회
        List<TeacherDTO.Read> teachers = teacherService.findTeachersBySubject(subject);

        // [2] JSP에 전달할 데이터 추가
        model.addAttribute("subject", subject);
        model.addAttribute("teachers", teachers);

        log.info("Loaded {} teachers for subject {}" , teachers.size(), subject);

        // [3] JSP 경로 반환
        return ViewUtils.returnView(model, "layer/lecture/teacher_list");
    }
}
