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
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class ChatHandshakeInterceptor implements HandshakeInterceptor {

    private final CacheManager cacheManager;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // HTTP 서블릿 요청인 경우에만 수행
        if (request instanceof ServletServerHttpRequest servletRequest) {

            // [1] HttpServletRequest 조회
            var servlet = servletRequest.getServletRequest();

            // [2] 세션 내 회원번호 조회
            HttpSession session = SessionUtils.getSession(servlet);

            if (Objects.nonNull(session)) {
                Long memberId = (Long) session.getAttribute(Param.MEMBER_ID);
                MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);

                Long id = profile.getMemberId();
                String role = profile.getRole().getRoleName();
                attributes.put("role", role);
                attributes.put("memberId", id);
                log.warn("🤝 Handshake 완료 → memberId = {}, role = {}", id, role);
            }

            /*
            // ✅ roomId는 URL 쿼리에서 가져오기
            String query = servlet.getQueryString(); // ex: roomId=1
            if (query != null && query.startsWith("roomId=")) {
                attributes.put("roomId", Long.parseLong(query.split("=")[1]));
            }

             */
        }


        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }
}
