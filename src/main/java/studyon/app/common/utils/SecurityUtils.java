package studyon.app.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import studyon.app.infra.security.dto.CustomUserDetails;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

    public static boolean isLogin() {

        // [1] SecurityContextHolder 내 Authentication 인증객체 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // [2] 로그인 판별 후 반환 (인증 객체가 존재하고, 익명 사용자가 아닌 경우 true)
        return Objects.nonNull(authentication) && !(authentication instanceof AnonymousAuthenticationToken);
    }


    public static CustomUserDetails getUserDetails() {

        // [1] SecurityContextHolder 내 Authentication 인증객체 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // [2] 인증 객체 반환 (로그인 회원인 경우)
        return isLogin() ? (CustomUserDetails) authentication : null;
    }
}
