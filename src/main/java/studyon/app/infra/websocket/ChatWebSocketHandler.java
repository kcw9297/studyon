package studyon.app.infra.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import studyon.app.common.constant.Param;
import studyon.app.common.enums.Role;
import studyon.app.layer.domain.chat.Chat;
import studyon.app.layer.domain.chat.repository.ChatRepository;
import studyon.app.layer.domain.chat_room.repository.ChatRoomRepository;
import studyon.app.layer.domain.member.repository.MemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    private final Map<Long, WebSocketSession> userSessions = new HashMap<>();
    private WebSocketSession adminSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        String role = (String) attributes.get("role");
        Long memberId = (Long) attributes.get("memberId");

        log.info("🔗 WebSocket 연결됨 / memberId={} / role={}", memberId, role);

        if (Objects.equals(role, Role.ROLE_ADMIN.getRoleName())) {
            adminSession = session;
            log.info("✅ 관리자 연결 완료: {}", memberId);
        } else {
            userSessions.put(memberId, session);
            log.info("👤 사용자 연결 완료: {}", memberId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject json = new JSONObject(message.getPayload());
        String msg = json.optString("msg");
        Long roomId = json.optLong("roomId", -1);


        if ("ROOM_CHANGE".equals(json.optString("type"))) {
            Long newRoomId = json.optLong("roomId");
            session.getAttributes().put("currentRoomId", newRoomId);
            log.info("👁️ 관리자 현재 방 변경됨 → {}", newRoomId);
            return;
        }
        Map<String, Object> attributes = session.getAttributes();
        String role = (String) attributes.get("role");
        Long memberId = (Long) attributes.get("memberId");

        log.info("💬 받은 메시지 → memberId={} / role={} / roomId={} / msg={}", memberId, role, roomId, msg);

        var roomOpt = chatRoomRepository.findById(roomId);
        var memberOpt = memberRepository.findById(memberId);

        if (roomOpt.isPresent() && memberOpt.isPresent()) {
            Chat chat = Chat.builder()
                    .chatRoom(roomOpt.get())
                    .sender(memberOpt.get())
                    .message(msg)
                    .build();

            chatRepository.save(chat);
            log.info("💾 채팅 저장 완료 → roomId={}, senderId={}, msg={}", roomId, memberId, msg);
        } else {
            log.warn("⚠️ Chat 저장 실패 (room or member not found)");
        }

        JSONObject data = new JSONObject();
        data.put("type", Objects.equals(role, Role.ROLE_ADMIN.getRoleName()) ? "ADMIN" : "STUDENT");
        data.put("sender", memberId);
        data.put("msg", msg);
        data.put("roomId", roomId);

        if (Objects.equals(role, Role.ROLE_STUDENT.getRoleName())) {
            if (adminSession != null && adminSession.isOpen()) {
                Object currentRoom = adminSession.getAttributes().get("currentRoomId");
                if (Objects.equals(currentRoom, roomId)) {
                    adminSession.sendMessage(new TextMessage(data.toString()));
                    log.info("📤 [고객 → 관리자] 같은 방 메시지 전달 완료 roomId={}", roomId);
                } else {
                    log.info("🚫 [고객 → 관리자] 관리자가 다른 방을 보고 있음 (roomId={})", roomId);
                }
            }
        }

        else if (Objects.equals(role, Role.ROLE_ADMIN.getRoleName())) {
            chatRoomRepository.findById(roomId).ifPresent(room -> {
                Long targetMemberId = room.getUserId(); // 🔹 ChatRoom이 Member를 FK로 가지고 있어야 함
                WebSocketSession userSession = userSessions.get(targetMemberId);

                if (userSession != null && userSession.isOpen()) {
                    try {
                        userSession.sendMessage(new TextMessage(data.toString()));
                        log.info("📤 사용자({})에게 메시지 전달 완료 (roomId={})", targetMemberId, roomId);
                    } catch (Exception e) {
                        log.error("❌ 사용자에게 메시지 전송 실패", e);
                    }
                } else {
                    log.warn("⚠️ 대상 사용자 세션이 열려 있지 않음 (roomId={})", roomId);
                }
            });
        }

        session.sendMessage(new TextMessage(data.toString()));
    }
}
