package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * 회원의 역할 타입
 * @version 1.0
 * @author kcw97
 */
@Getter
public enum Role {

    // Spring Security 적용을 위해 "ROLE_" 추가
    ROLE_STUDENT("학생"),
    ROLE_TEACHER("선생님"),
    ROLE_ADMIN("관리자");

    private final String memberState;

    Role(String memberState) {
        this.memberState = memberState;
    }

    public static List<Role> get() {
        return Arrays.asList(Role.values());
    }
}
