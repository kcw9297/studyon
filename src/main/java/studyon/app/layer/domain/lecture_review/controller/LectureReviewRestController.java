package studyon.app.layer.domain.lecture_review.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.enums.AppStatus;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.lecture_review.service.LectureReviewService;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/lectures/reviews")
@RequiredArgsConstructor
public class LectureReviewRestController {


    private final LectureReviewService lectureReviewService;
    /**
     * [GET] 과목별 최신 강의 리뷰 목록 조회
     */
    @GetMapping("/recent/{subject}")
    public ResponseEntity<?> readRecentReviews(@ModelAttribute LectureDTO.Search rq,
                                               @RequestParam(defaultValue = "4") int count) {
        // [1] 최근 강의 수강평 조회 로그
        log.info("최근 수강평 GET 요청: 과목 [{}]의 최근 수강평 조회", rq.getSubject());
        // [2] 최근 강의 수강평 조회
        List<LectureReviewDTO.Read> result = lectureReviewService.readRecentLectureReviews(rq.getSubject(), count);
        // [3] 성공 응답 반환
        return RestUtils.ok(result);
    }
    /**
     * [GET] 특정 선생님의 수강평 조회
     * @param rq teacherId 포함
     */
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<?> getRecentReviews(@ModelAttribute TeacherDTO.Search rq) {
        log.info("GET 요청: 선생님 ID [{}]의 최근 수강평 조회", rq.getTeacherId());
        // [1] 과목별로 선생님 정보 가져와서 리스팅(카운트 변수 테스트 하드코딩)
        int count = 10;
        List<LectureReviewDTO.Read> reviews = lectureReviewService.readRecentReview(rq.getTeacherId(), count);
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(reviews);
    }

    /**
     * [POST] 특정 선생님의 수강평 조회
     * @param dto 리뷰 DTO
     */
    @PostMapping("/create")
    public ResponseEntity<?> createReview(@ModelAttribute LectureReviewDTO.Write dto, HttpSession session) {
        log.info("[API] 리뷰 등록 요청 - lectureId={}, memberId={}, rating={}",
                dto.getLectureId(), dto.getMemberId(), dto.getRating());
        MemberProfile profile = SessionUtils.getProfile(session);
        Long memberId = profile.getMemberId();
        dto.setMemberId(memberId);

        lectureReviewService.createReview(dto, dto.getMemberId());

        return RestUtils.ok();
    }


    /**
     * [GET] 특정 강의의 수강평 목록 조회 (DTO 기반)
     */
    /**
     * ✅ [GET] 특정 강의의 수강평 목록 조회
     * 예: GET /api/lectures/reviews/2
     */
    @GetMapping("/{lectureId}")
    public ResponseEntity<?> getLectureReviews(@PathVariable Long lectureId) {
        log.info("🎯 수강평 조회 요청: lectureId={}", lectureId);

        List<LectureReviewDTO.Read> reviews = lectureReviewService.readLectureReviews(lectureId);

        return RestUtils.ok(reviews);
    }



}
