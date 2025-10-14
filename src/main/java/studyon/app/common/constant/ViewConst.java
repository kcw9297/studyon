package studyon.app.common.constant;

import java.util.Map;

/**
 *
 */
public class ViewConst {

    // 일반 상수
    public static final String APPLICATION_JSON = "application/json";
    public static final String UTF_8 = "UTF-8";
    public static final String OAUTH2 = "oauth2";

    // 안내 메세지 상수
    public static final String NOTICE_SERVER_ERROR = "오류가 발생했습니다.\n잠시 후에 시도해 주세요";

    // error field name
    public static final String ERROR = "error";
    public static final String ERROR_GLOBAL = ERROR + "_global";


    // error field
    public static final Map<String, String> FIELD_GLOBAL_ERROR = Map.of(ERROR_GLOBAL, NOTICE_SERVER_ERROR);

}
