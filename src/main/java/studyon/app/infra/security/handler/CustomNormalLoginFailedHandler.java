package studyon.app.infra.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.Msg;
import studyon.app.common.constant.Param;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.security.exception.WithdrawalException;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.HttpUtils;

import java.io.IOException;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-16) : kcw97 소셜회원 로직과 분리
 */

/**
 * Spring Security 일반회원 로그인 실패 처리 핸들러 클래스
 * @version 1.1
 * @author kcw97
 */
@Slf4j
@Component
public class CustomNormalLoginFailedHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 탈퇴 전 회원은 다른 곳으로 안내하는 로직 추가 필수 (BeforeWithdrawalException)
        
        // [1] 현재 실패한 URL 조회
        String url = request.getRequestURI();

        // 만약 정규 예외 이외의 내용이 발생하면, "InternalAuthenticationServiceException" 로 감싸져서 반환
        Throwable rootCause = exception instanceof InternalAuthenticationServiceException ? exception.getCause() : exception;

        log.warn("[Login Failed] url = {}, exception = {}, rootCause = {}",
                url, exception.getClass().getSimpleName(), rootCause.getClass().getSimpleName());

        // [2] 오류 상황 전달
        // 일반 회원의 이메일이 존재하지 않거나, 이메일과 비밀번호가 일치하지 않은 경우
        if (rootCause instanceof UsernameNotFoundException || rootCause instanceof BadCredentialsException)
            HttpUtils.jsonFail(
                    response,
                    StrUtils.toJson(Rest.Response.fail(Param.ERROR_GLOBAL, Msg.INCORRECT_EMAIL_PASSWORD)),
                    HttpServletResponse.SC_BAD_REQUEST
            );

        // 회원 탈퇴가 완료된 회원인 경우
        else if (rootCause instanceof WithdrawalException)
            HttpUtils.jsonFail(
                    response,
                    StrUtils.toJson(Rest.Response.fail(Param.ERROR_GLOBAL, Msg.WITHDRAWAL)),
                    HttpServletResponse.SC_FORBIDDEN
            );

        // 정지된 회원인 경우
        else if (rootCause instanceof DisabledException)
            HttpUtils.jsonFail(
                    response,
                    StrUtils.toJson(Rest.Response.fail(Param.ERROR_GLOBAL, Msg.DISABLED)),
                    HttpServletResponse.SC_FORBIDDEN
            );

        // 그 밖의 기타 예기치 않은 오류로 실패한 경우
        else
            HttpUtils.jsonFail(
                    response,
                    StrUtils.toJson(Rest.Response.fail(Param.ERROR_GLOBAL, Msg.SERVER_ERROR)),
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

    }

}
