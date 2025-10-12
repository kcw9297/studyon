package studyon.app.common.infra.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import studyon.app.common.constant.AppProfile;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * "local" 프로필의 웹 페이지에 필요한 기본 값을 주입하는 인터셉터 클래스
 * @version 1.0
 * @author kcw97
 */
@Profile(AppProfile.LOCAL)
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalInterceptor implements HandlerInterceptor {

    @Value("${local.file.domain}")
    private String fileDomain;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // [1] 필요 값 삽입
        request.setAttribute("fileDomain", fileDomain);
        return true;
    }
}
