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
public class OAuth2Attributes {

    private String nickname; // 임의 값 생성
    private String socialId;
    private Provider provider;
    private String nameAttributeKey;
    private Map<String, Object> attributes;

    /* 소셜 유형 별 매핑 메소드 */

    /**
     * 구글 소셜회원 전용 DTO 생성
     * @param attributes 로그인 응답 데이터 Map
     * @param nameAttributeKey OAuth2UserRequest userNameAttributeName
     * @return 구글 회원정보 DTO
     */
    public static OAuth2Attributes ofGoogle(Map<String, Object> attributes, String nameAttributeKey) {
        return createBuilder(attributes, nameAttributeKey, Provider.GOOGLE)
                .socialId((String) attributes.get("sub")) // 구글의 소셜회원번호 : "sub"
                .build();
    }

    /**
     * 네이버 소셜회원 전용 DTO 생성
     * @param attributes 로그인 응답 데이터 Map
     * @param nameAttributeKey OAuth2UserRequest userNameAttributeName
     * @return 구글 회원정보 DTO
     */
    private static OAuth2Attributes ofNaver(Map<String, Object> attributes, String nameAttributeKey) {

        // 네이버는 JSON 응답 내, "response" key 내 회원정보 존재
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return createBuilder(attributes, nameAttributeKey, Provider.NAVER)
                .socialId((String) response.get("id")) // 네이버의 소셜회원번호 : "id"
                .build();
    }

    // 공통 정보 삽입
    private static OAuth2AttributesBuilder createBuilder(Map<String, Object> attributes, String nameAttributeKey, Provider provider) {
        return OAuth2Attributes.builder()
                .nickname(StrUtils.createRandomNumString(8, provider.name()))
                .provider(provider)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey);
    }

}
