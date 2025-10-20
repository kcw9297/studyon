package studyon.app.layer.controller.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import studyon.app.infra.mail.dto.MailVerifyRequest;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.chat.service.ChatService;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.mail.manager.MailManager;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final CacheManager cacheManager;
    private final MailManager mailManager;
    private final ChatService chatService;

    /**
     * 회원 프로필 저장 (Form 데이터)
     * 예: POST /test/redis/profile
     *     memberId=1&nickname=yjs&email=test@test.com&role=PARENT
     */
    @ResponseBody
    @PostMapping("/redis/profile")
    public Object saveProfile(HttpSession session, MemberProfile profile) {
        cacheManager.saveProfile(profile.getMemberId(), profile);
        return "✅ Redis에 회원 프로필 저장 완료: memberId=" + profile.getMemberId();
    }

    /**
     * 회원 프로필 조회
     * 예: GET /test/redis/profile?memberId=1
     */
    @ResponseBody
    @GetMapping("/redis/profile")
    public Object getProfile(@RequestParam Long memberId) {
        return cacheManager.getProfile(memberId, MemberProfile.class);
    }

    /**
     * 최근 검색어 기록
     * 예: POST /test/redis/search
     *     memberId=1&keyword=SpringBoot
     */
    @ResponseBody
    @PostMapping("/redis/search")
    public Object recordSearch(Long memberId, String keyword) {
        cacheManager.recordLatestSearch(memberId, keyword);
        return "✅ 검색어 기록 완료: " + keyword;
    }

    /**
     * 최근 검색어 목록 조회
     * 예: GET /test/redis/search?memberId=1
     */
    @ResponseBody
    @GetMapping("/redis/search")
    public Object getSearchList(Long memberId) {
        return cacheManager.getLatestSearchList(memberId);
    }




    /**
     * [1] 세션에 값 저장
     * 예: POST /test/session/save
     *     key=username&value=yjs
     */
    @ResponseBody
    @PostMapping("/session/save")
    public Object saveSession(HttpSession session,
            String key, String value, Long memberId) {

        session.setAttribute(key, value);
        session.setAttribute("memberId", memberId);
        cacheManager.recordLogin(memberId, session.getId());
        return "✅ 세션 저장 완료: (" + key + " = " + value + "), sessionId = " + session.getId();
    }

    /**
     * [2] 세션 값 조회
     * 예: GET /test/session/get?key=username
     */
    @ResponseBody
    @GetMapping("/session/get")
    public Object getSessionValue(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        System.out.println("▶ key param = [" + key + "]");
        if (session == null) return "⚠️ 세션이 만료되었습니다";
        Object value = session.getAttribute(key);
        if (value == null) return "⚠️ 세션에 '" + key + "' 값이 없습니다.";
        return Map.of("sessionId", session.getId(), "key", key, "value", value);
    }

    /**
     * [3] 세션 무효화 (로그아웃처럼)
     * 예: DELETE /test/session/invalidate
     */
    @ResponseBody
    @DeleteMapping("/session/invalidate")
    public Object invalidateSession(HttpServletRequest request) {

        HttpSession session = SessionUtils.getSession(request);

        if (session != null) {
            session.invalidate();
            return "🧹 세션이 무효화되었습니다.";
        }

        return "⚠️ 이미 무효화된 세션입니다";
    }


    /**
     * 인증 코드 메일 전송 테스트
     * 예: POST /test/mail/verify
     */
    @ResponseBody
    @PostMapping("/mail/verify")
    public Object testSendVerify(HttpServletRequest request, String to) {
        return mailManager.sendVerifyCode(to, Duration.ofMinutes(1), SessionUtils.getSessionId(request, true));
    }

    /**
     * 인증 코드 메일 전송 테스트
     * 예: POST /test/mail/verify/cdde
     */
    @ResponseBody
    @PostMapping("/mail/verify/code")
    public Object testSendVerifyCode(HttpServletRequest request, String code) {

        MailVerifyRequest mailVerifyRequest =
                cacheManager.getMailRequest(SessionUtils.getSessionId(request), MailVerifyRequest.class);

        log.warn("mailRequest = {}", mailVerifyRequest);
        return Objects.isNull(mailVerifyRequest) || !mailVerifyRequest.checkVerifyCode(code) ?
                "인증 실패!!" : "인증 성공!!";
    }

    @GetMapping("/websocket")
    public String websocket() {
        return "test/websocket";
    }

    @ResponseBody
    @PostMapping("/chatbot")
    public Object chatbot(String question) {
        return Rest.Response.ok(Rest.Message.of("요청 성공", chatService.getAnswer(question)));
    }

    @GetMapping("/write")
    public String write() {
        return "test/write";
    }




    @PostMapping("/api/editor/write")
    public ResponseEntity<?> write(String title, String content) {
        log.warn("content = {}", content);
        return RestUtils.ok(Rest.Message.of("글 작성 성공"));
    }

}