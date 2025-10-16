package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusCode {

    public static final int OK = 0;
    public static final int INTERNAL_ERROR = 1; // 기타 예기치 않은 서버 오류
    public static final int LOGIN_SERVICE_ERROR = 2; // 로그인이 필요한 서비스에 접근
    public static final int FORBIDDEN_ERROR = 3; // 권한이 없는 사람이 접근한 경우 (미구매 강의 접근, 관리자 페이지 등)
}
