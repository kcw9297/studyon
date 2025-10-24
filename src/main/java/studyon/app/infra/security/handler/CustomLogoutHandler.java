package studyon.app.infra.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.Url;
import studyon.app.common.utils.SecurityUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.security.dto.CustomUserDetails;
import studyon.app.layer.base.utils.RestUtils;

import java.io.IOException;
import java.util.Objects;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-17) : kcw97 로그아웃 사용자 캐시 제거 추가
 */

/**
 * Spring Security Logout 처리 핸들러 클래스
 * @version 1.1
 * @author kcw97
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {

    private final CacheManager cacheManager;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // [1] 로그아웃 사용자 목록에서 제외
        String sessionId = SecurityUtils.getSessionId(authentication);
        CustomUserDetails userDetails = SecurityUtils.getUserDetails(authentication);

        log.warn("sessionId = {}, userDetails = {}", sessionId, userDetails);

        // TODO 수정해야 함
        if (Objects.nonNull(userDetails) && Objects.nonNull(sessionId))
            cacheManager.removeLogout(userDetails.getMemberId());

        // [2] 로그아웃 응답 반환
        RestUtils.jsonOK(response, Url.INDEX);
    }
}
