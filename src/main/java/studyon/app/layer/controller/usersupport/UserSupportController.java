package studyon.app.layer.controller.usersupport;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.enums.View;
import studyon.app.infra.cache.manager.CacheManager;
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

    private final CacheManager cacheManager;
    private final ChatService chatService;

    @GetMapping("/startchat")
    public String startchat(Model model, HttpServletRequest request) {
        // ✅ 세션에서 로그인한 Member 객체 가져오기
        Long memberId = SessionUtils.getMemberId(request);
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        log.info(profile.toString());
        return ViewUtils.returnView(model, View.USERSUPPORT, "start_chat");
    }

    @GetMapping("/chat")
    public String chatPage(@RequestParam("roomId") Long roomId, Model model) {
        model.addAttribute("roomId", roomId);
        log.info("💬 상담 채팅방 입장: {}", roomId);
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
