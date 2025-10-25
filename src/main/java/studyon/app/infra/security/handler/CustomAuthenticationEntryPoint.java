package studyon.app.infra.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.base.utils.RestUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * 로그인하지 않은 사용자가 로그인이 필요한 서비스에 접근 시 요청 처리 클래스
 * @version 1.0
 * @author kcw97
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // [1] 요청 URL 확인
        String requestURI = request.getRequestURI();

        // [2] API 요청 / 단순 View 페이지 접근 구분하여 처리
        if (requestURI.startsWith(Url.API)) handleApiRequest(request, response); // api 요청 처리
        else handleViewRequest(request, response); // view 요청 처리
    }

    // API 요청 처리
    private void handleApiRequest(HttpServletRequest request, HttpServletResponse response) {

        // [1] 헤더에 담긴 리다이렉트 주소 정보 확인
        String requestedFrom = request.getHeader("X-Requested-From");
        String redirect = Objects.isNull(requestedFrom) ?
                Url.LOGIN : // 현재 View URL 정보가 없으면 로그인 페이지로
                "%s?redirect=%s".formatted(Url.LOGIN, StrUtils.encodeToUTF8(requestedFrom)); // 정보가 있으면 리다이렉트 정보를 포함

        // [2] JSON 실패 응답 반환
        RestUtils.writeJsonFail(response, AppStatus.SECURITY_LOGIN_REQUIRED, redirect);
    }


    // View 요청 처리 (로그인 페이지로 Redirect)
    private void handleViewRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // [1] 요청 주소정보 추출
        String requestUrl = request.getRequestURI(); // 요청한 주소 (되돌아갈 장소)
        String queryString = request.getQueryString(); // 쿼리 스트링 존재 시 추가
        if (Objects.nonNull(queryString)) requestUrl += "?%s".formatted(queryString);

        // [2] 리다이렉트 주소 생성
        String redirect = "%s?redirect=%s".formatted(Url.LOGIN, StrUtils.encodeToUTF8(requestUrl));

        // [3] 리다이렉트 수행
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 ERROR
        response.sendRedirect(redirect);
    }
}
