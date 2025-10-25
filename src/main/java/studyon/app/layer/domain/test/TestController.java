package studyon.app.layer.domain.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.enums.View;
import studyon.app.infra.file.FileManager;
import studyon.app.infra.mail.dto.MailVerifyRequest;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.chat.service.ChatService;
import studyon.app.layer.domain.file.FileDTO;
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
        return "page/test/websocket";
    }

    @ResponseBody
    @PostMapping("/chatbot")
    public Object chatbot(String question) {
        return Rest.Response.ok(chatService.getAnswer(question));
    }


}