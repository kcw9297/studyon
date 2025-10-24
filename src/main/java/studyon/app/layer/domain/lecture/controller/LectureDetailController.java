package studyon.app.layer.domain.lecture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;

@Slf4j
@Controller
@RequestMapping(URL.LECTURE)
@RequiredArgsConstructor
public class LectureDetailController {
    private final LectureRepository lectureRepository;

    @GetMapping("/detail/{lectureId}")
    public String lecture_detail(@PathVariable Long lectureId, Model model) {
        Lecture lecture = lectureRepository.findWithTeacherById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다. id=" + lectureId));

        model.addAttribute("lecture", lecture);
        model.addAttribute("teacher", lecture.getTeacher());

        return ViewUtils.returnView(model, View.LECTURE, "lecture_detail");
    }

}