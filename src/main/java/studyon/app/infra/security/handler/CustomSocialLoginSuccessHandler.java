package studyon.app.infra.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.URL;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.domain.member.service.MemberService;

import java.io.IOException;
import java.util.Objects;

/**
 * Spring Security 소셜 로그인 성공처리 핸들러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // 이전 요청을 저장하고 있는 Cache
    private final RequestCache requestCache = new HttpSessionRequestCache();

    // 회원 정보를 기반으로 프로필 정보 저장
    private final MemberService memberService;
    private final CacheManager cacheManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // [1] RequestCache 내 SavedRequest 조회
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // [2] 만일 이전에 접근한 주소가 있으면, 그 주소로 Redirect
        String redirectUrl = Objects.isNull(savedRequest) ? URL.HOME : savedRequest.getRedirectUrl();
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
