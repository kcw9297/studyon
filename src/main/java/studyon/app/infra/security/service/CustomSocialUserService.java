package studyon.app.infra.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.enums.Provider;
import studyon.app.infra.security.dto.CustomUserDetails;
import studyon.app.infra.security.dto.OAuth2UserInfo;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;

import java.util.List;
import java.util.Map;


/**
 * 소셜 로그인 회원정보(OAuth2User)를 조회할 SocialUserService
 * @version 1.0
 * @author kcw97
 */

@Transactional // 실제로 조회/저장을 수행하므로 필수
@Component
@RequiredArgsConstructor
public class CustomSocialUserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // [1] 소셜 로그인 요청 조회 (OAuth2User 원본)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // [2] 소셜회원정보 DTO 생성
        OAuth2UserInfo oAuth2UserInfo = UserServiceUtils.getAttributes(oAuth2User, userRequest);

        // [3] 소셜 회원정보에 대응하는 회원 엔티티 조회
        // 만약 아직 가입하지 않은 경우, 즉시 회원가입 처리 후 회원 엔티티 반환 처리
        Member socialMember = getMember(oAuth2UserInfo);

        // [4] 소셜회원 정보를 담은 Custom UserDetails 생성 및 반환
        return UserServiceUtils.createUserDetails(socialMember, oAuth2UserInfo);
    }

    // 소셜 회원정보 조회. 존재하지 않는 경우 가입처리 수행
    private Member getMember(OAuth2UserInfo attributes) {

        return memberRepository
                .findByProviderIdAndProvider(attributes.getProviderId(), attributes.getProvider())
                .map(UserServiceUtils::checkWithdrawal) // 탈퇴회원 검증
                .map(member -> member.loginSocial(attributes.getEmail())) // 로그인 성공 시, 소셜정보 갱신 수행
                .orElseGet(() -> joinSocialMember(attributes));// 조회되지 않으면, 신규 가입처리 수행
    }

    // 신규 소셜회원가입 처리 수행
    private Member joinSocialMember(OAuth2UserInfo attributes) {

        return memberRepository.save(
                Member.joinSocialStudent(attributes.getEmail(), attributes.getNickname(), attributes.getProvider(), attributes.getProviderId())
        );
    }



}
