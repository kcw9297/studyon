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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    //private final ChatRoomRepository chatRoomRepository;
    //private final ChatRepository chatRepository;
    //private final MemberRepository memberRepository;

    private final Map<String, WebSocketSession> memberSessions = new HashMap<>();
    private WebSocketSession agentSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        // [1] session 내 attributes 조회
        Map<String, Object> attributes = session.getAttributes();

        // [2] WebSocketSession 내 회원정보 조회
        String role = (String) attributes.get(Param.ROLE);
        String memberId = String.valueOf(attributes.get(Param.MEMBER_ID));

        // [3]
        if (Objects.equals(role, Role.ROLE_ADMIN.getRoleName())) {
            agentSession = session;
            log.info("✅ 관리자 접속");

        } else {
            memberSessions.put(memberId, session);
            log.info("👤 고객 접속: {}", memberId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        JSONObject json = new JSONObject(message.getPayload());
        String role = json.optString(Param.ROLE);
        String memberId = json.optString(Param.MEMBER_ID);
        String msg = json.optString(Param.MSG);
        int roomId = json.optInt("roomId", -1); // ✅ 프론트에서 보낸 roomId 받기
        System.out.println("💬 받은 메시지: " + msg + " / roomId=" + roomId);


        /*
        // ✅ 닉네임 조회
        String nickname = memberRepo.findById(Integer.parseInt(memberId))
                .map(Member::getNickname)
                .orElse("익명");

        // ✅ 프론트에서 받은 roomId 기준으로 ChatRoom 찾기
        ChatRoom room = roomRepo.findById((long) roomId)
                .orElseThrow(() -> new IllegalArgumentException("❌ 존재하지 않는 roomId: " + roomId));

        // ✅ DB에 채팅 저장
        chatRepo.save(Chat.builder()
                .chatRoom(room)
                .senderId(memberId)
                .message(msg)
                .createdAt(LocalDateTime.now())
                .build());

         */

        // ✅ 메시지 생성 및 전송
        JSONObject data = new JSONObject();
        data.put("type", Objects.equals(role, Role.ROLE_ADMIN.getRoleName()) ? Role.ROLE_ADMIN.getRoleName() : Role.ROLE_STUDENT.getRoleName());
        data.put("sender", memberId);
        data.put("nickname", "nickname001");
        data.put("msg", msg);
        data.put("roomId", roomId);

        // 상담사 ↔ 고객 전송
        if (Objects.equals(role, Role.ROLE_STUDENT.getRoleName()) && Objects.nonNull(agentSession)) {
            agentSession.sendMessage(new TextMessage(data.toString()));

        } else if (Objects.equals(role, Role.ROLE_ADMIN.getRoleName())) {

            // 상담사가 메시지 보낼 때 고객 세션이 연결돼 있다면 보내기
            for (WebSocketSession userSession : memberSessions.values())
                if (userSession.isOpen()) userSession.sendMessage(new TextMessage(data.toString()));

        }

        // ✅ 본인에게도 표시
        session.sendMessage(new TextMessage(data.toString()));
    }
}
