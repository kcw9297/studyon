package studyon.app.layer.domain.support;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studyon.app.common.constant.Env;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.common.utils.EnvUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.chat.ChatDTO;
import studyon.app.layer.domain.chat.service.ChatService;
import studyon.app.layer.domain.chat_room.ChatRoom;
import studyon.app.layer.domain.chat_room.service.ChatRoomService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/support")
@RequiredArgsConstructor
public class AdminSupportController {

    private final ChatRoomService chatRoomService;
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




    /**
     * ✅ 관리자 상담 페이지 렌더링
     * URL: /admin/support
     */
    @GetMapping
    public String supportPage(Model model, HttpServletRequest request) {
        log.info("🧑‍💼 관리자 상담 페이지 진입");
        List<ChatRoom> rooms = chatRoomService.getAllRooms();
        log.info("💬 불러온 채팅방 목록 ({}개): {}", rooms.size(), rooms);
        model.addAttribute("rooms", rooms);
        model.addAttribute("chatUrl", chatUrl);
        log.warn(chatUrl);
        return ViewUtils.returnView(model, View.ADMIN, "admin_support");
    }

    /**
     * ✅ 비동기 채팅방 목록 조회 (JSON)
     * URL: /admin/support/rooms
     */
    @GetMapping("/rooms")
    @ResponseBody
    public ResponseEntity<List<ChatRoom>> getChatRooms() {
        List<ChatRoom> rooms = chatRoomService.getAllRooms();
        log.info("💬 채팅방 목록 {}개 반환", rooms.size());
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/messages/{roomId}")
    @ResponseBody
    public ResponseEntity<List<ChatDTO.Read>> getMessages(@PathVariable Long roomId) {
        log.info("📨 채팅방 [{}] 메시지 조회 요청", roomId);
        List<ChatDTO.Read> messages = chatService.getMessagesByRoomId(roomId);
        log.info("📥 조회된 메시지 {}건", messages.size());
        return ResponseEntity.ok(messages);
    }
}
