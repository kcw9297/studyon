package studyon.app.layer.controller.usersupport;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.chat_room.service.ChatRoomService;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/usersupport")
@RequiredArgsConstructor
public class UserSupportApiController {

    private final ChatRoomService chatRoomService;
    private final CacheManager cacheManager;

    @PostMapping("/create-room")
    public Map<String, Object> createChatRoom(HttpServletRequest request) {
        // ✅ 세션에서 로그인한 Member 객체 가져오기
        Long memberId = SessionUtils.getMemberId(request);
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);

        if (profile == null) {
            log.warn("❌ 로그인 세션 없음 - 채팅방 생성 불가");
            return Map.of("error", "로그인이 필요합니다");
        }

        Long userId = profile.getMemberId(); // ✅ memberId 사용
        String nickname = profile.getNickname();

        // ✅ 채팅방 생성 or 기존 방 조회
        Long roomId = chatRoomService.createRoomForUser(userId);
        log.info("✅ [{}] 새 채팅방 생성: roomId={}", nickname, roomId);

        return Map.of("roomId", roomId);
    }
}
