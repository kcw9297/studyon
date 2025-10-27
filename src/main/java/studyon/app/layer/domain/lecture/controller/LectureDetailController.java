package studyon.app.layer.domain.lecture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(Url.LECTURE)
@RequiredArgsConstructor
public class LectureDetailController {
    private final LectureRepository lectureRepository;
    private final LectureReviewRepository lectureReviewRepository;

    @GetMapping("/detail/{lectureId}")
    public String lecture_detail(@PathVariable Long lectureId, Model model) {

        Lecture lecture = lectureRepository.findWithTeacherById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다. id=" + lectureId));

        List<LectureReview> reviews = lectureReviewRepository.findByLectureIdWithMemberOrderByRatingDesc(lectureId);
        long reviewCount = lectureReviewRepository.countByLecture_LectureId(lectureId);

        model.addAttribute("lecture", lecture);
        model.addAttribute("teacher", lecture.getTeacher());
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewCount", reviewCount);

        return ViewUtils.returnView(model, View.LECTURE, "lecture_detail");
    }
}