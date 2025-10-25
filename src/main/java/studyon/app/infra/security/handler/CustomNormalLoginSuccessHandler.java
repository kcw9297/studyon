package studyon.app.infra.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.Param;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Role;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.security.dto.CustomUserDetails;
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
        SessionUtils.setAttribute(request, Param.MEMBER_ID, memberId);

        // [3] URL 내 redirect 파라미터 조회
        String redirect = request.getParameter(Param.REDIRECT);

        // [4] redirect 여부, 관리자 여부 판단 후 응답 반환
        if (userDetails.getAuthorities().stream().anyMatch(authority -> Objects.equals(authority.getAuthority(), Role.ROLE_ADMIN.name())))
            RestUtils.writeJsonOK(response, Url.ADMIN); // 관리자는 어드민 홈으로 이동

        else if (Objects.nonNull(redirect)) RestUtils.writeJsonOK(response, redirect); // 돌아갈 url이 있는 경우
        else RestUtils.writeJsonOK(response, Url.INDEX); // 돌아갈 곳이 없는 경우, 기본 홈으로 이동
    }

}
