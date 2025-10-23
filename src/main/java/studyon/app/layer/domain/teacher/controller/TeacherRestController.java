package studyon.app.layer.domain.teacher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.common.constant.URL;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.service.TeacherService;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-21) : khj00 최초 작성
 */

/**
 * 선생님 JSON 데이터 불러오는 컨트롤러 클래스
 * @version 1.0
 * @author khj00
 */

@Slf4j
@RestController
@RequestMapping(URL.TEACHERS_API)
@RequiredArgsConstructor
public class TeacherRestController {

    // [0] 선생님 서비스 불러오기
    private final TeacherService teacherService;

    /**
     * [POST] 모든 선생님 정보 가져오기
     * @return 모든 선생님 정보
     */

    @PostMapping
    public ResponseEntity<?> getAllTeachers(@ModelAttribute TeacherDTO.Search rq) {
        log.info(" 선생님 전체 정보 POST 요청: 모든 선생님 조회");
        // [1] 모든 선생님 정보 가져와서 리스팅
        List<TeacherDTO.Read> teachers = teacherService.readAllTeachers();
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(Rest.Message.of("모든 선생님을 불러왔습니다."), teachers);
    }

    /**
     * [POST] 과목별 선생님 정보 가져오기
     * @return 해당 과목 선생님들 정보
     */
    @PostMapping( "/subject/{subject}")
    public ResponseEntity<?> getTeachersBySubject(@ModelAttribute TeacherDTO.Search rq) {
        log.info(" POST 요청: 과목 [{}]의 선생님 조회", rq.getSubject());
        // [1] 과목별로 선생님 정보 가져와서 리스팅
        List<TeacherDTO.Read> teachersBySubject = teacherService.readTeachersBySubject(rq.getSubject());
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(Rest.Message.of("해당 과목 선생님들을 불러왔습니다.", teachersBySubject.toString()), teachersBySubject);
    }

    /**
     * [POST] 특정 선생님의 수강평 조회
     * @param rq teacherId 포함
     */
    @PostMapping("/reviews/{teacherId}")
    public ResponseEntity<?> getRecentReviews(@ModelAttribute TeacherDTO.Search rq) {
        log.info("POST 요청: 선생님 ID [{}]의 최근 수강평 조회", rq.getTeacherId());
        // [1] 과목별로 선생님 정보 가져와서 리스팅(카운트 변수 테스트 하드코딩)
        int count = 10;
        List<LectureReviewDTO.Read> reviews = teacherService.readRecentReview(rq.getTeacherId(), count);
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(Rest.Message.of("해당 선생님의 최근 리뷰를 불러왔습니다.", reviews.toString()), reviews);
        //return RestUtils.ok(reviews);
    }

    /**
     * [POST] 과목별 선생님 정보 가져오기
     * @return 해당 과목 선생님들 정보
     */
    @PostMapping( "/profile/recentLecture")
    public ResponseEntity<?> getRecentLecture(@ModelAttribute TeacherDTO.Search rq) {
        log.info(" POST 요청: 선생님 ID [{}]의 최근 등록된 강의 조회", rq.getTeacherId());
        // [1] 선생님 정보 가져와서 최근 등록된 강의 리스팅
        int count = 5;
        List<LectureDTO.Read> recentLectures = teacherService.readRecentLectures(rq.getTeacherId(), count);
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(Rest.Message.of("해당 선생님 최근 강의를 불러왔습니다.", recentLectures.toString()), recentLectures);
    }

    /**
     * [POST] 과목별 선생님 정보 가져오기
     * @return 해당 과목 선생님들 정보
     */
    @PostMapping( "/profile/bestLecture")
    public ResponseEntity<?> getBestLecture(@ModelAttribute TeacherDTO.Search rq) {
        log.info(" POST 요청: 선생님 ID [{}]의 인기 강의(수강생 순) 조회", rq.getTeacherId());
        // [1] 선생님 정보 가져와서 인기 강의 리스팅
        int count = 5;
        List<LectureDTO.Read> bestLectures = teacherService.readBestLectures(rq.getTeacherId(), count);
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(Rest.Message.of("해당 선생님 인기 강의를 불러왔습니다.", bestLectures.toString()), bestLectures);
    }
}
