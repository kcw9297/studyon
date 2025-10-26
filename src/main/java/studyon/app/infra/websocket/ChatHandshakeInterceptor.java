package studyon.app.infra.websocket;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import studyon.app.common.constant.Param;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class ChatHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            var servlet = servletRequest.getServletRequest();
            HttpSession session = SessionUtils.getSession(servlet);

            if (Objects.nonNull(session)) {
                Long memberId = (Long) session.getAttribute(Param.MEMBER_ID);
                MemberProfile profile = SessionUtils.getProfile(session);

                Long id = profile.getMemberId();
                String role = profile.getRole().getRole();
                attributes.put("role", role);
                attributes.put("memberId", id);
                log.warn("Handshake 완료 → memberId = {}, role = {}", id, role);
            }
        }
        return true;
    }

    //구현체라서 구현만 해놓음 사용X
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
