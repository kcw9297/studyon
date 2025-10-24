package studyon.app.layer.domain.payment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member_lecture.service.MemberLectureService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentRestController {

    private final MemberLectureService memberLectureService;
    private final CacheManager cacheManager;

    /**
     * ✅ 결제 완료 시 member_lecture 등록 API
     * URL: POST /api/payment/complete
     */
    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody Map<String, Long> body, HttpServletRequest request) {
        Long lectureId = body.get("lectureId");
        Long memberId = SessionUtils.getMemberId(request);

        // ✅ 로그인 여부 확인
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        if (profile == null) {
            log.warn("🚫 비로그인 상태에서 결제 요청 발생");

            // ✅ 로그인 페이지로 리다이렉트 (302 Redirect)
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/login");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }

        // ✅ 결제 성공 → member_lecture 등록
        memberLectureService.enrollLecture(memberId, lectureId);
        log.info("✅ 결제 완료 - memberId={}, lectureId={}", memberId, lectureId);

        return RestUtils.ok();
    }
}
