package studyon.app.layer.domain.member_lecture.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member_lecture.MemberLectureDTO;
import studyon.app.layer.domain.member_lecture.service.MemberLectureService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MemberLectureRestController {

    private final MemberLectureService memberLectureService;

    /**
     * 🔹 내 강의 목록 조회 API
     *  GET /api/mypage/lectures?subject=korean
     */
    @GetMapping("/lectures")
    public ResponseEntity<?> getMyLectures(
            @RequestParam(defaultValue = "all") String subject,
            HttpServletRequest request
    ) {
        // 세션에서 로그인된 사용자 식별
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 강의 목록 요청");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
        }

        log.info("📘 내 강의 목록 조회 요청 | memberId={}, subject={}", memberId, subject);

        // 서비스 호출 (subject 필터링)
        List<MemberLectureDTO.Read> lectures =
                memberLectureService.getLecturesByMemberAndSubject(memberId, subject);

        return ResponseEntity.ok(lectures);
    }


}
