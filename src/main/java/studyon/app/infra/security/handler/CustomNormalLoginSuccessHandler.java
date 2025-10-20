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
import studyon.app.common.constant.Param;
import studyon.app.common.constant.URL;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.security.dto.CustomUserDetails;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * Spring Security 일반 로그인 성공처리 핸들러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomNormalLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // 이전 요청을 저장하고 있는 Cache
    private final RequestCache requestCache = new HttpSessionRequestCache();

    // 회원 정보를 기반으로 프로필 정보 저장
    private final CacheManager cacheManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // [1] 로그인 유저 정보 추출
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long memberId = userDetails.getMemberId();
        String sessionId = SessionUtils.getSessionId(request);

        // [2] 로그인 회원유저 기록
        cacheManager.recordLogin(memberId, sessionId);
        SessionUtils.setSession(request, Param.MEMBER_ID, memberId);

        // [3] RequestCache 내 SavedRequest 조회
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // [4] 만일 이전에 접근한 주소가 있으면, 그 주소로 Redirect
        String redirectUrl = Objects.isNull(savedRequest) ? URL.INDEX : savedRequest.getRedirectUrl();
        RestUtils.jsonOK(response, StrUtils.toJson(Rest.Response.ok(redirectUrl)));
    }
}
