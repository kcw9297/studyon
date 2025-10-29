package studyon.app.layer.domain.lecture.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;
import studyon.app.layer.domain.lecture_index.service.LectureIndexService;
import studyon.app.layer.domain.lecture_question.LectureQuestionDTO;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-22) : khj00 생성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 응답 형식 변경
 */

/**
 * 강의(일단 추천 강의 페이지 중심) 비동기(REST API) 컨트롤러 클래스
 * @version 1.1
 * @author khj00
 */

@Slf4j
@RestController
@RequestMapping("/api/lecture")
@RequiredArgsConstructor
public class LectureRestController {
    private final LectureService lectureService;
    private final LectureIndexService lectureIndexService;
    /**
     * [GET] 과목별 최신 강의 목록 조회
     */
    @GetMapping("/recent")
    public ResponseEntity<?> readRecentLectures(@ModelAttribute LectureDTO.Search rq,
                                                @RequestParam(defaultValue = "4") int count) {
        // [1] 최신 강의 조회 로그
        log.info("✅ [GET] 최신 강의 조회 요청");
        // [2] 최신 강의 조회
        List<LectureDTO.Read> result = lectureService.readRecentLectures(rq.getSubject(), count);
        // [3] 성공 응답 반환
        return RestUtils.ok(result);
    }

    /**
     * [GET] 과목별 인기 강의(수강생 수 순) 목록 조회
     */
    @GetMapping("/best")
    public ResponseEntity<?> readBestLectures(@ModelAttribute LectureDTO.Search rq,
                                              @RequestParam(defaultValue = "4") int count) {
        // [1] 인기 강의 조회 로그
        log.info("✅ [GET] 인기 강의 조회 요청");
        // [2] 인기 강의 조회
        List<LectureDTO.Read> result = lectureService.readBestLectures(rq.getSubject(), count);
        // [3] 성공 응답 반환
        return RestUtils.ok(result);
    }

    /**
     * [GET] 과목별 선생님 정보 가져오기
     * @return 해당 과목 선생님들 정보
     */
    @GetMapping( "/profile/recentLecture")
    public ResponseEntity<?> getRecentLecture(@ModelAttribute TeacherDTO.Search rq, @RequestParam(defaultValue = "5") int count) {
        log.info(" GET 요청: 선생님 ID [{}]의 최근 등록된 강의 조회", rq.getTeacherId());
        // [1] 선생님 정보 가져와서 최근 등록된 강의 리스팅
        List<LectureDTO.Read> recentLectures = lectureService.readRecentLectures(rq.getTeacherId(), count);
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(recentLectures);
    }

    /**
     * [GET] 과목별 선생님 정보 가져오기
     * @return 해당 과목 선생님들 정보
     */
    @GetMapping( "/profile/bestLecture")
    public ResponseEntity<?> getBestLecture(@ModelAttribute TeacherDTO.Search rq) {
        log.info(" GET 요청: 선생님 ID [{}]의 인기 강의(수강생 순) 조회", rq.getTeacherId());
        // [1] 선생님 정보 가져와서 인기 강의 리스팅
        int count = 5;
        List<LectureDTO.Read> bestLectures = lectureService.readBestLectures(rq.getTeacherId(), count);
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(bestLectures);
    }

    @GetMapping("/video/lectureindex/{lectureId}")
    public ResponseEntity<?> getLectureVideoIndexes(@PathVariable Long lectureId, HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long memberId = profile.getMemberId();

        List<LectureIndexDTO.Read> response = lectureIndexService.readMemberAllByLectureId(lectureId);
        return RestUtils.ok(response);
    }
}
