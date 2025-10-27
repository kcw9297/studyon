package studyon.app.layer.domain.lecture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.Subject;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.*;

@Slf4j
@Controller
@RequestMapping(Url.LECTURE)
@RequiredArgsConstructor
public class LectureDetailController {
    private final LectureRepository lectureRepository;
    private final LectureReviewRepository lectureReviewRepository;
    private final LectureService lectureService;

    @GetMapping("/detail/{lectureId}")
    public String lecture_detail(@PathVariable Long lectureId, Model model) {

        Lecture lecture = lectureRepository.findWithTeacherById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다. id=" + lectureId));

        /* 리뷰 */
        List<LectureReview> reviews = lectureReviewRepository.findByLectureIdWithMemberOrderByRatingDesc(lectureId);
        long reviewCount = lectureReviewRepository.countByLecture_LectureId(lectureId);
        Map<Integer, Double> ratingPercent = lectureService.getRatingPercentage(lectureId);

        /* 알고리즘 - 리스트 */
        LectureDTO.Search lectureSearch = LectureDTO.Search.builder().subject(lecture.getSubject()).build();
        TeacherDTO.Search teacherSearch = TeacherDTO.Search.builder().teacherId(lecture.getTeacher().getTeacherId()).build();
        List<LectureDTO.Read> recommendedBySubject = lectureService.readBestLectures(lectureSearch.getSubject(), 4);
        List<LectureDTO.Read> recommendedByTeacher = lectureService.readBestLectures(teacherSearch.getTeacherId(), 4);

        Map<Long, Long> reviewCountMap = new HashMap<>();
        for (LectureDTO.Read rec : recommendedBySubject) {
            long count = lectureReviewRepository.countByLecture_LectureId(rec.getLectureId());
            reviewCountMap.put(rec.getLectureId(), count);
        }
        for (LectureDTO.Read rec : recommendedByTeacher) {
            long count = lectureReviewRepository.countByLecture_LectureId(rec.getLectureId());
            reviewCountMap.put(rec.getLectureId(), count);
        }

        model.addAttribute("lecture", lecture);
        model.addAttribute("teacher", lecture.getTeacher());
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("ratingPercent", ratingPercent);
        model.addAttribute("recommendedBySubject", recommendedBySubject);
        model.addAttribute("recommendedByTeacher", recommendedByTeacher);
        model.addAttribute("reviewCountMap", reviewCountMap);

        return ViewUtils.returnView(model, View.LECTURE, "lecture_detail");
    }

}