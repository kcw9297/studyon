package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

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

    // 특정 상수 값
    // 만료 시간 (분)
    public static final int EXPIRATION_WITHDRAWAL_DAY = 3;

    // error field name
    public static final String ERROR = "error";
    public static final String ERROR_GLOBAL = ERROR + "_global";
}
