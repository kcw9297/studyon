package studyon.app.infra.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.ViewUtils;

import java.io.IOException;

/**
 * 권한이 부족한 요청을 처리하는 핸들러 클래스 (로그인 사용자 대상)
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // [1] 요청 URL 확인
        String requestURI = request.getRequestURI();

        // [2] API 요청 / 단순 View 페이지 접근 구분하여 처리
        if (requestURI.startsWith(Url.API)) handleApiRequest(response); // api 요청 처리
        else handleViewRequest(request, response); // view 요청 처리

    }

    // API 요청 처리
    private void handleApiRequest(HttpServletResponse response) {
        RestUtils.writeJsonFail(response, AppStatus.SECURITY_ACCESS_DENIED);
    }


    // View 요청 처리
    private void handleViewRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 ERROR
        request.getRequestDispatcher("/WEB-INF/views/error/403.jsp").forward(request, response);
    }
}
