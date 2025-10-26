package studyon.app.layer.domain.payment.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member_lecture.service.MemberLectureService;

import java.util.Map;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-24) : khs97 최초 작성
 *  ▶ ver 1.1 (2025-10-26) : kcw97 프로필 획득 로직 수정
 */

/**
 * 강의 결제 처리 요청을 받는 클래스
 * @version 1.1
 * @author khs97
 */

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentRestController {

    private final MemberLectureService memberLectureService;

    /**
     * ✅ 결제 완료 시 member_lecture 등록 API
     * URL: POST /api/payment/complete
     */
    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody Map<String, Long> body, HttpSession session) {

        Long lectureId = body.get("lectureId");
        MemberProfile profile = SessionUtils.getProfile(session);

        // ✅ 결제 성공 → member_lecture 등록
        memberLectureService.enroll(profile.getMemberId(), lectureId);
        log.info("✅ 결제 완료 - memberId={}, lectureId={}", profile.getMemberId(), lectureId);

        return RestUtils.ok();
    }
}
