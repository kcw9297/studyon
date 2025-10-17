package studyon.app.infra.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.URL;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.HttpUtils;
import studyon.app.layer.base.utils.SessionUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * Spring Security Logout 처리 핸들러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {

    private final CacheManager cacheManager;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpUtils.jsonOK(
                response,
                StrUtils.toJson(Rest.Response.ok(Rest.Message.of("로그아웃 성공"), URL.HOME)
        ));
    }
}
