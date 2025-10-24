package studyon.app.infra.security.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Spring Security 일반/소셜 로그인 회원정보 객체
 * @version 1.0
 * @author kcw97
 */

@Builder
@Getter
@ToString
@AllArgsConstructor
public class CustomUserDetails implements UserDetails, OAuth2User, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 기본 사용자 정보
    private Long memberId;
    private String email;
    private String password;
    private String nickname;
    private Boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;

    // 소셜 사용자 추가 정보
    private String providerId;
    private Map<String, Object> attributes;

    /* --------- UserDetails 구현 --------- */

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override // 활성화 상태 (정지회원 등)
    public boolean isEnabled() {
        return isActive;
    }

    @Override // 활성화 계정이나, 잠긴 상태인 경우 (비밀번호 입력 5회 이상 실패 등)
    public boolean isAccountNonLocked() {
        return true; // 사용 x
    }

    @Override // 계정 만료일이 지난 경우 (계정 유효기간 등)
    public boolean isAccountNonExpired() {
        return true; // 사용 x
    }

    @Override // 비밀번호 만료 여부 (비밀번호 변경 90일 초과 등)
    public boolean isCredentialsNonExpired() {
        return true; // 사용 x
    }

    /* --------- OAuth2User 구현 --------- */

    @Override
    public String getName() {
        return providerId;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
