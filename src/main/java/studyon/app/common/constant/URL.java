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

    // base url (entity)
    public static final String INDEX = "/";
    public static final String HOME = "/home";
    public static final String API = "/api";
    public static final String ADMIN = "/admin";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/header";
    public static final String TEACHER = "/teacher";
    public static final String TEACHERS =  "/teachers";
    public static final String LECTURE = "/lecture";
    public static final String LECTURES = "/lectures";
    public static final String MEMBER = "/member";
    public static final String MEMBERS = "/members";
    public static final String EDITOR = "/editor";

    // Security
    public static final String LOGIN_PROCESS = LOGIN + "/process";

    /* MEMBER */
    public static final String MEMBER_API = API + MEMBERS;
    public static final String MEMBER_ADMIN_API = ADMIN + MEMBER_API;
    public static final String MEMBER_ADMIN = ADMIN + MEMBER;




    // 정적 상수 모음
    public static final String[] STATIC_RESOURCE_PATHS = {
            "/WEB-INF/**", "/css/**", "/js/**", "/img/**",
            "/favicon.ico", "/webjars/**", "/.well-known/**", "/error/**"
    };

}
