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
public enum Provider {

    NORMAL("일반"),
    GOOGLE("구글"),
    KAKAO("카카오"),
    NAVER("네이버");

    private final String value;

    Provider(String value) {
        this.value = value;
    }

    public static List<Provider> get() {
        return Arrays.asList(Provider.values());
    }
}
