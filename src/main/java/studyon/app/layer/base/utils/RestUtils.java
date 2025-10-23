package studyon.app.layer.base.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import studyon.app.common.constant.Param;
import studyon.app.common.exception.UtilsException;
import studyon.app.layer.base.dto.Rest;

import java.io.IOException;
import java.util.Map;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestUtils {


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
        response.setContentType(Param.APPLICATION_JSON);
        response.setCharacterEncoding(Param.UTF_8);
        response.setStatus(status);
        response.getWriter().write(message);
    }

    public static ResponseEntity<?> ok() {
        return ResponseEntity.ok().build();
    }

    public static ResponseEntity<?> ok(Object data) {
        return new ResponseEntity<>(Rest.Response.ok(data), HttpStatus.OK);
    }

    public static ResponseEntity<?> ok(Rest.Message message) {
        return new ResponseEntity<>(Rest.Response.ok(message), HttpStatus.OK);
    }

    public static ResponseEntity<?> ok(Rest.Message message, Object data) {
        return new ResponseEntity<>(Rest.Response.ok(message, data), HttpStatus.OK);
    }

    public static ResponseEntity<?> ok(Rest.Message message, String redirect) {
        return new ResponseEntity<>(Rest.Response.ok(message, redirect), HttpStatus.OK);
    }

    public static ResponseEntity<?> fail400(Rest.Message message) {
        return new ResponseEntity<>(Rest.Response.fail(1, message), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> fail400(Rest.Message message, String redirect) {
        return new ResponseEntity<>(Rest.Response.fail(1, message, redirect), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> fail400(Map<String, String> fieldErrors) {
        return new ResponseEntity<>(Rest.Response.fail(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> fail400(String errorField, String errorMessage) {
        return new ResponseEntity<>(Rest.Response.fail(Map.of(errorField, errorMessage)), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> fail500(Rest.Message message) {
        return new ResponseEntity<>(Rest.Response.fail(-1, message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<?> fail500(Rest.Message message, String redirect) {
        return new ResponseEntity<>(Rest.Response.fail(-1, message, redirect), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
