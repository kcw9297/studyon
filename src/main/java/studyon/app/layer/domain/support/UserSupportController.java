package studyon.app.layer.domain.support;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Env;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.common.utils.EnvUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.chat.ChatDTO;
import studyon.app.layer.domain.chat.service.ChatService;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/usersupport")
@RequiredArgsConstructor
public class UserSupportController {

    private final ChatService chatService;
    private final Environment env;

    @Value("${app.domain}")
    private String appDomain;

    // 프로필 판별 값
    private boolean isLocal;
    private boolean isProd;
    private String chatUrl;

    // 빈 초기화 후 앱 시작 전 호출
    @PostConstruct
    private void init() {
        this.isLocal = EnvUtils.hasProfile(env, Env.PROFILE_LOCAL);
        this.isProd = EnvUtils.hasProfile(env, Env.PROFILE_PROD);
        String protocol = isProd ? "https" : "http";
        this.chatUrl = "%s://%s%s".formatted(protocol, appDomain, Url.SOCKET_CHAT);
    }


    @GetMapping("/startchat")
    public String startchat(Model model, HttpSession session) {
        // ✅ 세션에서 로그인한 Member 객체 가져오기
        MemberProfile profile = SessionUtils.getProfile(session);
        log.info(profile.toString());
        return ViewUtils.returnView(model, View.USERSUPPORT, "start_chat");
    }

    @GetMapping("/chat")
    public String chatPage(@RequestParam("roomId") Long roomId, Model model) {
        model.addAttribute("roomId", roomId);
        log.info("💬 상담 채팅방 입장: {}", roomId);
        model.addAttribute("chatUrl", chatUrl);
        return ViewUtils.returnView(model, View.USERSUPPORT, "chat");
    }

    @GetMapping("/messages/{roomId}")
    @ResponseBody
    public ResponseEntity<List<ChatDTO.Read>> getMessages(@PathVariable Long roomId) {
        log.info("📨 사용자 채팅방 [{}] 메시지 조회 요청", roomId);
        List<ChatDTO.Read> messages = chatService.getMessagesByRoomId(roomId);
        log.info("📥 조회된 메시지 {}건", messages.size());
        return ResponseEntity.ok(messages);
    }
}
