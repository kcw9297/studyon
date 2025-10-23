package studyon.app.layer.domain.lecture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.layer.base.utils.RestUtils;
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
    public ResponseEntity<?> readRecentLectures(@ModelAttribute LectureDTO.Search rq) {
        // [1] 최신 강의 조회 로그
        log.info("최신 강의 POST 요청: 과목 [{}]의 최신 강의 조회", rq.getSubject());
        // [2] 4개씩 보여줄 예정(하드코딩)
        int count = 4;
        // [3] 최신 강의 조회
        List<LectureDTO.Read> result = lectureService.readRecentLectures(rq.getSubject(), count);
        // [4] 성공 응답 반환
        return RestUtils.ok(result);
    }

    /**
     * [POST] 과목별 인기 강의(수강생 수 순) 목록 조회
     */
    @PostMapping("/best")
    public ResponseEntity<?> readBestLectures(@ModelAttribute LectureDTO.Search rq) {
        // [1] 인기 강의 조회 로그
        log.info("인기 강의 POST 요청: 과목 [{}]의 인기 강의 조회", rq.getSubject());
        // [2] 4개씩 보여줄 예정(하드코딩)
        int count = 4;
        // [3] 인기 강의 조회
        List<LectureDTO.Read> result = lectureService.readBestLectures(rq.getSubject(), count);
        // [4] 성공 응답 반환
        return RestUtils.ok(result);
    }
    /**
     * [POST] 과목별 최신 강의 리뷰 목록 조회
     */
    @PostMapping("/recent/reviews")
    public ResponseEntity<?> readRecentReviews(@ModelAttribute LectureDTO.Search rq) {
        // [1] 최근 강의 수강평 조회 로그
        log.info("최근 수강평 POST 요청: 과목 [{}]의 최근 수강평 조회", rq.getSubject());
        // [2] 4개씩 보여줄 예정(하드코딩)
        int count = 4;
        // [3] 최근 강의 수강평 조회
        List<LectureReviewDTO.Read> result = lectureService.readRecentLectureReviews(rq.getSubject(), count);
        // [4] 성공 응답 반환
        return RestUtils.ok(result);
    }
}
