package studyon.app.layer.base.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import studyon.app.common.constant.Param;
import studyon.app.common.constant.Url;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {

    public static String createSessionKey(String namespace, String sessionId) {
        return "%s:sessions:%s".formatted(namespace, sessionId);
    }

    /**
     * 세션 조회 (신규 생성 x)
     * @param request HttpServletRequest
     * @return session
     */
    public static HttpSession getSession(HttpServletRequest request) {
        return getSession(request, false);
    }

    /**
     * 세션 조회
     * @param request HttpServletRequest
     * @param create 신규 생성 여부
     * @return session
     */
    public static HttpSession getSession(HttpServletRequest request, boolean create) {
        return request.getSession(create);
    }

    /**
     * 세션 아이디 조회 (신규 생성 x)
     * @param request HttpServletRequest
     * @return sessionId
     */
    public static String getSessionId(HttpServletRequest request) {
        return getSessionId(request, false);
    }

    /**
     * 세션 아이디 조회
     * @param request HttpServletRequest
     * @param create 신규 생성 여부
     * @return sessionId
     */
    public static String getSessionId(HttpServletRequest request, boolean create) {

        // [1] 세션 조회
        HttpSession session = getSession(request, create);

        // [2] 세션이 조회되지 않으면 null, 조회된 세션아이디 반환
        return Objects.isNull(session) ? null : session.getId();
    }


    public static Long getMemberId(HttpServletRequest request) {

        // [1] 세션 조회
        HttpSession session = getSession(request);

        // 세션이 없는 경우 null 반환
        if (Objects.isNull(session)) return null;

        // [2]
        Object attrMemberId = session.getAttribute(Param.MEMBER_ID);
        return Objects.isNull(attrMemberId) ? null : (Long) attrMemberId;
    }



    public static Long getMemberId(HttpSession session) {
        Object attr = Objects.requireNonNull(session.getAttribute(Param.MEMBER_ID));
        return (Long) attr;
    }


    public static Long getTeacherId(HttpSession session) {
        Object attrTeacherId = session.getAttribute(Param.TEACHER_ID);
        return Objects.isNull(attrTeacherId) ? null : (Long) attrTeacherId;
    }


    public static MemberProfile getProfile(HttpSession session) {
        Object attr = Objects.requireNonNull(session.getAttribute(Param.PROFILE), "프로필 정보가 존재하지 않습니다!");
        return (MemberProfile) attr;
    }

    public static <T> T getValue(HttpServletRequest request, String paramName) {

        // [1] 세션 조회
        HttpSession session = getSession(request);

        // 세션이 없는 경우 null 반환
        if (Objects.isNull(session)) return null;

        // [2]
        Object attrParam = session.getAttribute(paramName);
        return Objects.isNull(attrParam) ? null : (T) attrParam;
    }


    public static void setAttribute(HttpServletRequest request, String paramName, Object paramValue) {

        // [1] 세션 조회
        HttpSession session = getSession(request);

        // 세션이 없는 경우 null 반환
        if (Objects.isNull(session)) return;

        // [2] 세션 데이터 삽입
        session.setAttribute(paramName, paramValue);
    }




    /**
     * 세션 조회
     * @param request HttpServletRequest
     * @return 세션 내 저장된 redirect 주소
     */
    public static String getRedirectUrl(HttpServletRequest request) {

        // [1] 세션 조회
        HttpSession session = getSession(request, false);

        // [2] 세션이 조회되지 않으면 HOME 주소 반환
        if (Objects.isNull(session)) return Url.INDEX;
        Object attr = session.getAttribute(Param.REDIRECT);

        return Objects.isNull(attr) ? Url.INDEX : (String) attr;
    }

}
