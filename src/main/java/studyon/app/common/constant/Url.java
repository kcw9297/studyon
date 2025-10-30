package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * URL 상수
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Url {

    // 기본적으로 자주 쓰일 수 있는 보편적 URL 상수
    public static final String INDEX = "/";
    public static final String HOME = "/home";
    public static final String API = "/api";
    public static final String ADMIN = "/admin";
    public static final String MAIL = "/mail";
    public static final String VERIFY = "/verify";
    public static final String OAUTH2 = "/oauth2";
    public static final String LOGIN = "/login";
    public static final String JOIN = "/join";
    public static final String CODE = "/code";
    public static final String URL = "/url";

    // base url (entity)
    public static final String TEACHER = "/teacher";
    public static final String TEACHERS =  "/teachers";
    public static final String LECTURE = "/lecture";
    public static final String LECTURES = "/lectures";
    public static final String MEMBER = "/member";
    public static final String MEMBERS = "/members";
    public static final String SUBJECT = "/subject";
    public static final String REGISTER = "/register";
    public static final String EDITOR = "/editor";
    public static final String FILE = "/file";
    public static final String MYPAGE = "/mypage";
    public static final String RESULT = "/result";

    // Security, auth, account
    public static final String EDIT_PASSWORD = "/edit-password";
    public static final String LOGIN_PROCESS = LOGIN + "/process";
    public static final String LOGOUT = "/logout";
    public static final String NOTICES = "/notices";
    public static final String BANNERS = "/banners";
    public static final String PAYMENT = "/payment";
    public static final String PAYMENTS = "/payments";
    public static final String AUTH = "/auth";

    public static final String AUTH_EDIT_PASSWORD = AUTH + EDIT_PASSWORD;
    public static final String AUTH_API = API + AUTH;
    public static final String AUTH_API_VERIFY = AUTH_API + VERIFY;
    public static final String AUTH_API_MAIL = AUTH_API + MAIL;
    public static final String AUTH_API_MAIL_CODE = AUTH_API_MAIL + CODE;
    public static final String AUTH_API_MAIL_URL = AUTH_API_MAIL + URL;
    public static final String AUTH_JOIN_MAIL = JOIN + MAIL;
    public static final String AUTH_JOIN_MAIL_RESULT = AUTH_JOIN_MAIL + RESULT;

    /* MEMBER */
    public static final String MEMBER_API = API + MEMBERS;
    public static final String MEMBER_ADMIN_API = ADMIN + MEMBER_API;
    public static final String MEMBER_ADMIN = ADMIN + MEMBER;

    /* TEACHER */
    public static final String TEACHERS_API = API + TEACHERS;   //  "/api/teachers"

    /* LECTURE */
    public static final String LECTURES_API = API + LECTURES;   //  "/api/lecture"

    /* MYPAGE */
    public static final String MYPAGE_API = API + MYPAGE;

    /* HOME */
    public static final String HOME_API = API + HOME;    //  "/api/home"

    /* NOTICE */
    public static final String NOTICES_API = API + NOTICES;    //  "/api/home"
    public static final String ADMIN_NOTICES_API = ADMIN + NOTICES_API;    //  "/api/home"

    /* BANNER */
    public static final String BANNERS_API = API + BANNERS;    //  "/api/home"
    public static final String ADMIN_BANNERS_API = ADMIN + BANNERS_API;    //  "/api/home"

    /* PAYMENT */
    public static final String PAYMENTS_API = API + PAYMENTS;
    public static final String PAYMENTS_ADMIN_API = ADMIN + PAYMENTS_API;

    /* ADMIN */
    public static final String ADMIN_API = ADMIN + API;  // "/admin/api/home"
    public static final String ADMIN_HOME_API = ADMIN_API + HOME;

    // 정적 상수 모음
    public static final String[] STATIC_RESOURCE_PATHS = {
            "/WEB-INF/**", "/css/**", "/js/**", "/img/**",
            "/favicon.ico", "/webjars/**", "/.well-known/**", "/error/**"
    };

}
