package studyon.app.infra.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import studyon.app.common.constant.ViewConst;
import studyon.app.infra.security.exception.IncorrectEmailPasswordException;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.HttpUtils;

import java.io.IOException;

/**
 * Spring Security 로그인 실패 처리 핸들러 클래스
 * @version 1.0
 * @author kcw97
 */
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // [1] 현재 실패한 URL 조회
        String url = request.getRequestURI();
        log.warn("[Login Failed] url = {}", url);

        // [2] "oauth2" 요청이 아닌 경우, 오류 메세지 전달
        // 현재 소셜로그인 회원의 응답은 소셜 로그인 페이지에서 자체적으로 처리하고 있음 (서버에 실패정보 안 넘어옴)
        if (!url.contains(ViewConst.OAUTH2)) {

            // 아이디(이메일), 비밀번호가 일치하지 않은 경우
            if (exception instanceof IncorrectEmailPasswordException)
                HttpUtils.jsonFail(
                        response,
                        StrUtils.toJson(Rest.Response.fail(ViewConst.ERROR_GLOBAL, exception.getMessage())),
                        HttpServletResponse.SC_BAD_REQUEST
                );

            // 그 밖의 기타 예기치 않은 오류로 실패한 경우
            else
                HttpUtils.jsonFail(
                        response,
                        StrUtils.toJson(ViewConst.FIELD_GLOBAL_ERROR),
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );
        }
    }
}
