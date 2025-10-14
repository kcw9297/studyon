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

    private String id;      // 랜덤 생성 아이디 (엔티티 생성을 위해 존재)
    private String email;   // 랜덤 생성 이메일 (")
    private String socialId;
    private Provider provider;
    private String nameAttributeKey;
    private Map<String, Object> attributes;

    /* 소셜 유형 별 매핑 메소드 */

    public static OAuth2Attributes ofGoogle(Map<String, Object> attributes, String nameAttributeKey) {
        return OAuth2Attributes.builder()
                .id(StrUtils.createRandomId(Provider.GOOGLE.getValue()))
                .email(StrUtils.createRandomEmail(Provider.GOOGLE.getValue()))
                .socialId((String) attributes.get("sub")) // 구글의 소셜회원번호 : "sub"
                .provider(Provider.GOOGLE)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }


    private static OAuth2Attributes ofNaver(Map<String, Object> attributes, String nameAttributeKey) {

        // 네이버는 JSON 응답 내, "response" key에 회원정보 존재
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attributes.builder()
                .id(StrUtils.createRandomId(Provider.NAVER.getValue()))
                .email(StrUtils.createRandomEmail(Provider.NAVER.getValue()))
                .socialId((String) attributes.get("id")) // 네이버의 소셜회원번호 : "id"
                .provider(Provider.NAVER)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

}
