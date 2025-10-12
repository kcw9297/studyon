package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * 회원 소셜 로그인 제공자
 * @version 1.0
 * @author kcw97
 */
@Getter
public enum SocialProvider {

    GOOGLE("구글"),
    KAKAO("카카오"),
    NAVER("네이버");

    private final String name;

    SocialProvider(String name) {
        this.name = name;
    }

    public static List<SocialProvider> get() {
        return Arrays.asList(SocialProvider.values());
    }
}
