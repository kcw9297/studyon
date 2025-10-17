package studyon.app.infra.security.dto;

import lombok.*;
import studyon.app.common.enums.Provider;
import studyon.app.common.utils.StrUtils;

import java.util.Map;

/**
 * OAuth2 소셜 로그인 제공자로부터 정보를 임시 보관할 DTO
 * @version 1.0
 * @author kcw97
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2UserInfo {

    private String nickname;
    private String email; // 사용자가 얼마든 바꿀 수 있는 값 (UK 사용 금지)
    private Provider provider;
    private String providerId;
    private Map<String, Object> attributes; // 소셜로그인 API 응답 데이터

    /* 소셜 유형 별 매핑 메소드 */

    /**
     * 소셜회원 DTO 생성
     * @param attributes 로그인 응답 데이터 Map (API 응답 JSON 데이터)
     * @param provider 소셜 로그인 provider (OAuth2UserRequest 내 정보)
     * @return 각 소셜회원에 맞는 회원정보 DTO
     */
    public static OAuth2UserInfo of(Map<String, Object> attributes, Provider provider) {

        return switch (provider) {
            case GOOGLE -> ofGoogle(attributes, provider);
            case NAVER -> ofNaver(attributes, provider);
            default -> null;
        };
    }

    // 구글 소셜회원정보 DTO 생성
    public static OAuth2UserInfo ofGoogle(Map<String, Object> attributes, Provider provider) {
        return createBuilder(attributes, provider)
                .email((String) attributes.get("email"))
                .providerId((String) attributes.get("sub")) // 구글의 소셜회원번호 : "sub"
                .build();
    }

    // 네이버 소셜회원정보 DTO 생성
    public static OAuth2UserInfo ofNaver(Map<String, Object> attributes, Provider provider) {

        // 네이버는 JSON 응답 내, "response" key 내 회원정보 존재
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return createBuilder(attributes, provider)
                .email((String) response.get("email"))
                .providerId((String) response.get("id")) // 네이버의 소셜회원번호 : "id"
                .build();
    }

    // 공통 정보 삽입
    private static OAuth2UserInfoBuilder createBuilder(Map<String, Object> attributes, Provider provider) {

        // 닉네임은 비즈니스 규칙 상 중복이 불가능하므로, 소셜 회원에게 받아오지 않고 직접 고유 값 부여
        return OAuth2UserInfo.builder()
                .nickname(StrUtils.createRandomNumString(10, provider.name()))
                .provider(provider)
                .attributes(attributes);
    }

}
