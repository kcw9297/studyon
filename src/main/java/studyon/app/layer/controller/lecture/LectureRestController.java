package studyon.app.layer.controller.lecture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-22) : khj00 생성
 */

/**
 * 강의(일단 추천 강의 페이지 중심) 비동기(REST API) 컨트롤러 클래스
 * @version 1.0
 * @author khj
 */

@Slf4j
@RestController
@RequestMapping("/api/lecture")
@RequiredArgsConstructor
public class LectureRestController {
    private final LectureService lectureService;

    /**
     * [POST] 과목별 최신 강의 목록 조회
     */
    @PostMapping("/recent")
    public List<LectureDTO.Read> readRecentLectures(@ModelAttribute LectureDTO.Search rq) {
        log.info("최신 강의 POST 요청: 과목 [{}]의 최신 강의 조회", rq.getSubject());
        int count = 4;
        return lectureService.readRecentLectures(rq.getSubject(), count);
    }

    /**
     * [POST] 과목별 인기 강의(수강생 수 순) 목록 조회
     */
    @PostMapping("/best")
    public List<LectureDTO.Read> readBestLectures(@ModelAttribute LectureDTO.Search rq) {
        log.info("인기 강의 POST 요청: 과목 [{}]의 인기 강의 조회", rq.getSubject());
        int count = 4;
        return lectureService.readBestLectures(rq.getSubject(), count);
    }

    @PostMapping("/recent/reviews")
    public List<LectureReviewDTO.Read> readRecentReviews(@ModelAttribute LectureDTO.Search rq) {
        log.info("최근 수강평 POST 요청: 과목 [{}]의 최근 수강평 조회", rq.getSubject());
        int count = 4;
        return lectureService.readRecentLectureReviews(rq.getSubject(), count);
    }
}
