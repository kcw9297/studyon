package studyon.app.infra.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.HttpUtils;

import java.util.Objects;

/**
 * Spring Security Logout 처리 핸들러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final CacheManager cacheManager;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // [1] 현재 회원의 session 조회
        HttpSession session = HttpUtils.getSession(request);

        // [2] 무효화
        if (Objects.nonNull(session)) {
            String sessionId = session.getId();
            session.invalidate(); // 무효 처리


            // 추가 처리 (캐시 제거 등)
        }
    }
}
