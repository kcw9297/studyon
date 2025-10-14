package studyon.app.layer.base.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import studyon.app.common.constant.URLConst;
import studyon.app.layer.base.dto.RestResponse;
import studyon.app.common.exception.common.UtilsException;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

    /**
     * 세션 조회 (신규 생성 x)
     * @param request HttpServletRequest
     * @return 조회 세션 반환
     */
    public static HttpSession getSession(HttpServletRequest request) {
        return getSession(request, false);
    }

    /**
     * 세션 조회
     * @param request HttpServletRequest
     * @param create 신규 생성 여부
     * @return 조회 세션 반환
     */
    public static HttpSession getSession(HttpServletRequest request, boolean create) {
        return request.getSession(create);
    }

    /**
     * 세션 아이디 조회 (신규 생성 x)
     * @param request HttpServletRequest
     * @return 조회 세션번호 반환
     */
    public static String getSessionId(HttpServletRequest request) {
        return getSessionId(request, false);
    }

    /**
     * 세션 아이디 조회
     * @param request HttpServletRequest
     * @param create 신규 생성 여부
     * @return 조회 세션번호 반환
     */
    public static String getSessionId(HttpServletRequest request, boolean create) {
        return getSession(request, create).getId();
    }

    /**
     * response 사용하여 직접 JSON 응답 반환
     * @param response HttpServletResponse
     * @param message 응답 문자열 (JSON)
     * @throws UtilsException 응답 전달 실패 시
     */
    public static void jsonOK(HttpServletResponse response, String message) {

        try {
            writeJson(response, HttpServletResponse.SC_OK, message);

        } catch (Exception e) {
            throw new UtilsException("JSON 응답 생성에 실패했습니다!", e);
        }
    }

    /**
     * response 사용하여 직접 JSON 응답 반환
     * @param response HttpServletResponse
     * @param message 응답 문자열 (JSON)
     * @param status 응답 상태 (HttpServletResponse 상수 값)
     * @throws UtilsException 응답 전달 실패 시
     */
    public static void jsonFail(HttpServletResponse response, String message, int status) {

        try {
            writeJson(response, status, message);

        } catch (Exception e) {
            throw new UtilsException("JSON 응답 생성에 실패했습니다!", e);
        }
    }

    // JSON write
    private static void writeJson(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType(URLConst.APPLICATION_JSON);
        response.setCharacterEncoding(URLConst.UTF_8);
        response.setStatus(status);
        response.getWriter().write(message);
    }




    public static ResponseEntity<?> ok(String message, Object data) {
        return new ResponseEntity<>(RestResponse.ok(message, data), HttpStatus.OK);
    }

    public static ResponseEntity<?> ok(String message, String redirect) {
        return new ResponseEntity<>(RestResponse.ok(message, redirect), HttpStatus.OK);
    }

    public static ResponseEntity<?> fail400(String message) {
        return new ResponseEntity<>(RestResponse.fail(message, 1), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> fail400(String message, String redirect) {
        return new ResponseEntity<>(RestResponse.fail(message, redirect, 1), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> fail500(String message) {
        return new ResponseEntity<>(RestResponse.fail(message, -1), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<?> fail500(String message, String redirect) {
        return new ResponseEntity<>(RestResponse.fail(message, redirect, -1), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
