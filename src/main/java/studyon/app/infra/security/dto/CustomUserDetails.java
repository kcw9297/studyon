package studyon.app.infra.security.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Spring Security 일반/소셜 로그인 회원정보 객체
 * @version 1.0
 * @author kcw97
 */

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails, OAuth2User {

    // 기본 사용자 정보
    private Long memberId;
    private String loginId;
    private String password;
    private String nickname;
    private Boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;

    // 소셜 사용자 추가 정보
    private String socialId;
    private String nameAttributeKey;
    private Map<String, Object> attributes;


    /* --------- 정적 생성 메소드 --------- */

    public static CustomUserDetails createNormal(Long memberId, String loginId, String password, String nickname, Boolean isActive,
                                                 Collection<? extends GrantedAuthority> authorities) {
        return new CustomUserDetails(
                memberId, loginId, password, nickname, isActive, authorities, null, null, null
        );
    }

    public static CustomUserDetails createSocial(Long memberId, String password, String nickname, Boolean isActive,
                                                 Collection<? extends GrantedAuthority> authorities,
                                                 String socialId, String nameAttributeKey, Map<String, Object> attributes) {
        return new CustomUserDetails(
                memberId, null, password, nickname, isActive, authorities, socialId, nameAttributeKey, attributes
        );
    }

    /* --------- UserDetails 구현 --------- */

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public String getPassword() {
        return password;    // 인증에만 사용한 이후, setter 를 통해 값을 null 으로 변경
    }

    /**
     * 소셜 로그인 검증 후, 검증 객체 내 로그인 아이디/비밀번호 정보 제거
     */
    public void clearLoginInfo() {
        this.loginId = null;
        this.password = null;
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
        if (Objects.isNull(attributes)) return String.valueOf(memberId);
        Object keyValue = attributes.get(nameAttributeKey);
        return Objects.isNull(keyValue) ? String.valueOf(memberId) : keyValue.toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
