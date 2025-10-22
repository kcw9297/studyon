package studyon.app.layer.controller.lecture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Subject;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.lecture_review.service.LectureReviewService;
import studyon.app.layer.domain.teacher.service.TeacherService;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.1 (2025-10-20) : khj00 수정
 */

/**
 * 강의 서비스 연결 컨트롤러 클래스
 * @version 1.1
 * @author kcw97, khj00
 */


@Slf4j
@Controller
@RequestMapping(URL.LECTURE)
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;
    private final LectureReviewService lectureReviewService;

    /**
     * [GET] 해당하는 과목 추천 페이지
     * @param subject 해당 과목
     * @param count 보여지는 개수 조정을 위한 카운트 변수
     */
    @GetMapping("/recommend/{subject}")
    public String lectureRecommendView(@PathVariable Subject subject, Model model, @RequestParam(defaultValue = "4") int count) {
        // [1] 강의 목록들, 리뷰 생성
        List<LectureReviewDTO.Read> reviews = lectureReviewService.readSubjectReviews(subject, count);
        List<LectureDTO.Read> recentLectures = lectureService.readRecentLectures(subject, count);
        List<LectureDTO.Read> bestLectures = lectureService.readBestLectures(subject, count);
        // [2] 모델에 변수 바인딩
        model.addAttribute("recentLectures", recentLectures);
        model.addAttribute("bestLectures", bestLectures);
        model.addAttribute("reviews", reviews);
        model.addAttribute("subject", subject);
        // [3] 뷰 리턴
        return ViewUtils.returnView(model, View.LECTURE,"lecture_recommend");
    }
}
