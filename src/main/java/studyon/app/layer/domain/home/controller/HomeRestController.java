package studyon.app.layer.domain.home.controller;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-23) : khj00 생성
 */

/**
 * 홈화면 강의 목록 Rest API 컨트롤러
 * @version 1.0
 * @author khj00
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.LectureRegisterStatus;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;

import java.util.List;

/**
 * 강의(일단 추천 강의 페이지 중심) 비동기(REST API) 컨트롤러 클래스
 * @version 1.0
 * @author khj
 */

@Slf4j
@RestController
@RequestMapping(Url.HOME_API)
@RequiredArgsConstructor
public class HomeRestController {

    private final LectureService lectureService;

    /**
     * [GET] 홈화면 최신 강의 목록 조회
     */
    @GetMapping("/recent")
    public ResponseEntity<?> readAllRecentLectures(@ModelAttribute LectureDTO.Search rq, @RequestParam(defaultValue = "5") int count) {
        // [1] 최신 강의 조회 로그
        log.info("전체 최신 강의 GET 요청: 전체 최신 강의 조회");
        // [2] 전체 최신 강의 조회
        List<LectureDTO.Read> result = lectureService.readAllRecentLectures(count);
        // [3] 성공 응답 반환
        return RestUtils.ok(result);
    }

    /**
     * [GET] 홈화면 인기 강의 목록 조회(수강생 순)
     */
    @GetMapping("/best")
    public ResponseEntity<?> readAllBestLectures(@ModelAttribute LectureDTO.Search rq,
                                                 @RequestParam(defaultValue = "5") int count) {
        // [1] 인기 강의 조회 로그 (수강생 순)
        log.info("전체 인기 강의 GET 요청: 전체 인기 강의 조회");
        // [2] 전체 인기 강의 조회
        List<LectureDTO.Read> result = lectureService.readAllPopularLectures(count);
        // [3] 성공 응답 반환
        return RestUtils.ok(result);
    }
}
