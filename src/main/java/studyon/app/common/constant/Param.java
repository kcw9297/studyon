package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Param {

    // 일반 상수
    public static final String APPLICATION_JSON = "application/json";
    public static final String UTF_8 = "UTF-8";
    public static final String OAUTH2 = "oauth2";
    public static final String REDIRECT_URL = "redirectUrl";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String JSESSIONID = "JSESSIONID";
    public static final String SESSION = "SESSION";
    public static final String MEMBER_ID = "memberId";
    public static final String ERROR = "error";
    public static final String ROLE = "role";
    public static final String MSG = "msg";

    // redis key
    public static final String KEY_BACKUP = "BACKUP";
    public static final String KEY_CACHE = "CACHE";
    public static final String KEY_LOGIN = "LOGIN";


    // 특정 상수 값
    // 만료 시간 (분)
    public static final int EXPIRATION_TIME = 3;
    public static final int MAX_RECENT_SEARCH_KEYWORD = 10; // 최대 최근 검색어 개수
    public static final Duration EXPIRATION_CACHE = Duration.ofSeconds(30); // 캐시 만료시간

    // error field name
    public static final String ERROR_GLOBAL = ERROR + "Global";
}
