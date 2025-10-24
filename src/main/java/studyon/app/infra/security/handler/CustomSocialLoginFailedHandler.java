package studyon.app.infra.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.Msg;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;

import java.io.IOException;

/**
 * Spring Security 소샬 로그인 실패 처리 핸들러 클래스
 * @version 1.0
 * @author kcw97
 */
@Slf4j
@Component
public class CustomSocialLoginFailedHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 탈퇴 전 회원은 다른 곳으로 안내하는 로직 추가 필수
        
        // [1] 현재 실패한 URL 조회
        String url = request.getRequestURI();
        log.warn("[Login Failed] url = {}", url);

        // [2] 오류 상황 전달
        // 소셜 회원정보를 얻는데 실패한 경우 (OAuth2User 조회 실패)
        if (exception instanceof OAuth2AuthenticationException)
            RestUtils.jsonFail(response, AppStatus.SECURITY_OATH2_AUTHENTICATION_FAILED);

        // 기타 서버 오류가 발생한 경우
        else
            RestUtils.jsonFail(response, AppStatus.SERVER_ERROR);

    }
}
