package studyon.app.infra.security.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import studyon.app.common.constant.Msg;
import studyon.app.common.constant.Param;
import studyon.app.common.enums.Provider;
import studyon.app.infra.security.dto.CustomUserDetails;
import studyon.app.infra.security.dto.OAuth2UserInfo;
import studyon.app.infra.security.exception.BeforeWithdrawalException;
import studyon.app.infra.security.exception.WithdrawalException;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Spring Security UserService 지원 클래스
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class UserServiceUtils {

    static CustomUserDetails createUserDetails(Member member) {
        return CustomUserDetails.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .isActive(member.getIsActive())
                .authorities(List.of(new SimpleGrantedAuthority(member.getRole().name())))
                .build();
    }

    static CustomUserDetails createUserDetails(Member socialMember, OAuth2UserInfo oAuth2UserInfo) {
        return CustomUserDetails.builder()
                .memberId(socialMember.getMemberId())
                .email(socialMember.getEmail())
                .password(socialMember.getPassword())
                .nickname(socialMember.getNickname())
                .isActive(socialMember.getIsActive())
                .authorities(List.of(new SimpleGrantedAuthority(socialMember.getRole().name())))
                .providerId(socialMember.getProviderId())   // 소셜 회원번호
                .attributes(oAuth2UserInfo.getAttributes()) // 소셜 회원정보
                .build();
    }


    static OAuth2UserInfo getAttributes(OAuth2User oAuth2User, OAuth2UserRequest userRequest) {

        // [1] 소셜 로그인서비스 사용자 정보 추출
        Map<String, Object> attributes = oAuth2User.getAttributes(); // JSON 응답 데이터

        // [2] 사용자 소셜 유형 추출
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Provider provider = Provider.valueOf(registrationId.toUpperCase()); // 소셜 로그인 제공자 (GOOGLE, NAVER, ...)

        // [3] 추출한 소셜 회원정보 기반, 소셜 회원정보 DTO 생성 및 반환
        return OAuth2UserInfo.of(attributes, provider);
    }

    static Member checkWithdrawal(Member member) {

        // [1] 비활성 여부, 탈퇴 여부, 탈퇴 처리가 완료된 여부 확인
        boolean isInactive = !member.getIsActive();
        boolean isWithdrawal = Objects.nonNull(member.getWithdrawAt());
        boolean isWithdrawalOver = isWithdrawal && member.getWithdrawAt().plusDays(Param.EXPIRATION_TIME).isAfter(LocalDateTime.now());

        // [2] 검증 수행
        if (isInactive) {

            if (isWithdrawal && isWithdrawalOver)
                throw new BeforeWithdrawalException("탈퇴 전 회원"); // 테스트 메세지 (실제로는 탈퇴복구 로직으로 해야함)
            else if (isWithdrawal)
                throw new WithdrawalException("");
        }

        // [3] 검증에 성공한 회원 엔티티객체 그대로 반환 (메소드 체이닝)
        return member;
    }

}
