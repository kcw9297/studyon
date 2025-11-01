package studyon.app.layer.domain.lecture.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;
import studyon.app.layer.domain.lecture_index.service.LectureIndexService;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.*;

@Slf4j
@Controller
@RequestMapping(Url.LECTURE)
@RequiredArgsConstructor
public class LectureDetailController {
    private final LectureRepository lectureRepository;
    private final LectureReviewRepository lectureReviewRepository;
    private final LectureService lectureService;
    private final LectureIndexService lectureIndexService;

    @GetMapping("/detail/{lectureId}")
    public String lecture_detail(@PathVariable Long lectureId, Model model) {

        Lecture lecture = lectureRepository.findWithTeacherById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다. id=" + lectureId));

        /* 리뷰 */
        List<LectureReview> reviews = lectureReviewRepository.findReviewsWithMemberAndProfile(lectureId);
        long reviewCount = lectureReviewRepository.countByLecture_LectureId(lectureId);
        Map<Integer, Double> ratingPercent = lectureService.getRatingPercentage(lectureId);
        String thumbnailPath = lectureRepository.findThumbnailPathByLectureId(lectureId)
                .orElse(null);

        /* 알고리즘 - 리스트 */
        List<LectureDTO.Read> recommendedBySubject = lectureService.readBestLecturesBySubject(lecture.getSubject().name(), 4);
        List<LectureDTO.Read> recommendedByTeacher = lectureService.readBestLecturesByTeacher(lecture.getTeacher().getTeacherId(), 4);

        /* 알고리즘 계산(강사&과목) */
        Map<Long, Long> reviewCountMap = new HashMap<>();
        for (LectureDTO.Read rec : recommendedBySubject) {
            long count = lectureReviewRepository.countByLecture_LectureId(rec.getLectureId());
            reviewCountMap.put(rec.getLectureId(), count);
        }
        for (LectureDTO.Read rec : recommendedByTeacher) {
            long count = lectureReviewRepository.countByLecture_LectureId(rec.getLectureId());
            reviewCountMap.put(rec.getLectureId(), count);
        }

        //강의시간 계산 로직
        Long total = lecture.getTotalDuration(); // 초 단위 (nullable 방지)
        if (total == null) total = 0L;

        long hours = total / 3600L;
        long minutes = (total % 3600L) / 60L;
        long seconds = total % 60L;


        model.addAttribute("lecture", lecture);
        model.addAttribute("teacher", lecture.getTeacher());
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("ratingPercent", ratingPercent);
        model.addAttribute("recommendedBySubject", recommendedBySubject);
        model.addAttribute("recommendedByTeacher", recommendedByTeacher);
        model.addAttribute("reviewCountMap", reviewCountMap);
        model.addAttribute("thumbnailPath", thumbnailPath);

        //강의시간 바로 사용
        model.addAttribute("hours", hours);
        model.addAttribute("minutes", minutes);
        model.addAttribute("seconds", seconds);


        return ViewUtils.returnView(model, View.LECTURE, "lecture_detail");
    }

    @GetMapping("/detail/curriculum/{lectureId}")
    public ResponseEntity<?> readCurriculum(@PathVariable Long lectureId, HttpSession session) {
        log.info("readCurriculum 호출");
        MemberProfile profile = SessionUtils.getProfile(session);
        Long memberId = profile.getMemberId();
        List<LectureIndexDTO.Read> response = lectureIndexService.readMemberAllByLectureId(lectureId);
        return RestUtils.ok(response);
    }


}