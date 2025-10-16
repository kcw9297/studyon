package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * URL 상수
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class URL {

    // base url
    public static final String HOME = "/";
    public static final String API = "/api";
    public static final String ADMIN = "/admin";
    public static final String PROCESS = "/process";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String TEACHER = "/teacher";
    public static final String TEACHERS =  "/teachers";
    public static final String LECTURE = "/lecture";
    public static final String LECTURES = "/lectures";
    public static final String MEMBER = "/member";
    public static final String MEMBERS = "/members";

    // business method url
    public static final String READ = "/read";
    public static final String JOIN = "/join";
    public static final String FIND = "/find";

    // Spring Security url
    public static final String LOGIN_PROCESS = LOGIN + PROCESS;

    // @RestController url
    public static final String API_MEMBERS = API + MEMBERS;

    // @Controller url




}
