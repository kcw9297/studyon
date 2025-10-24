package studyon.app.layer.base.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import studyon.app.common.constant.Param;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.UtilsException;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.base.dto.Rest;

import java.io.IOException;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestUtils {


    public static void jsonOK(HttpServletResponse response, String redirect) {

        try {
            writeJson(response, HttpServletResponse.SC_OK, StrUtils.toJson(Rest.Response.ok(redirect)));

        } catch (Exception e) {
            log.error("JSON 200 OK 응답처리 실패. 오류 : {}", e.getMessage());
            throw new UtilsException(AppStatus.UTILS_LOGIC_FAILED, e);
        }
    }


    public static void jsonOK(HttpServletResponse response, AppStatus appStatus) {

        try {
            writeJson(response, HttpServletResponse.SC_OK, StrUtils.toJson(Rest.Response.ok(appStatus)));

        } catch (Exception e) {
            log.error("JSON 200 OK 응답처리 실패. 오류 : {}", e.getMessage());
            throw new UtilsException(AppStatus.UTILS_LOGIC_FAILED, e);
        }
    }

    public static void jsonOK(HttpServletResponse response, AppStatus appStatus, String redirect) {

        try {
            writeJson(response, HttpServletResponse.SC_OK, StrUtils.toJson(Rest.Response.ok(appStatus, redirect)));

        } catch (Exception e) {
            log.error("JSON 200 OK 응답처리 실패. 오류 : {}", e.getMessage());
            throw new UtilsException(AppStatus.UTILS_LOGIC_FAILED, e);
        }
    }


    public static void jsonFail(HttpServletResponse response, AppStatus appStatus) {

        try {
            writeJson(response, appStatus.getHttpCode(), StrUtils.toJson(Rest.Response.fail(appStatus)));

        } catch (Exception e) {
            log.error("JSON 오류 응답처리 실패. 오류 : {}", e.getMessage());
            throw new UtilsException(AppStatus.UTILS_LOGIC_FAILED, e);
        }
    }

    public static void jsonFail(HttpServletResponse response, AppStatus appStatus, String redirect) {

        try {
            writeJson(response, appStatus.getHttpCode(), StrUtils.toJson(Rest.Response.fail(appStatus, redirect)));

        } catch (Exception e) {
            log.error("JSON 오류 응답처리 실패. 오류 : {}", e.getMessage());
            throw new UtilsException(AppStatus.UTILS_LOGIC_FAILED, e);
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

    public static ResponseEntity<?> ok(AppStatus appStatus) {
        return ok(appStatus, "");
    }

    public static ResponseEntity<?> ok(Object data) {
        return ok(data, AppStatus.OK);
    }

    public static ResponseEntity<?> ok(Object data, AppStatus appStatus) {
        return new ResponseEntity<>(Rest.Response.ok(StrUtils.toJson(data), appStatus), HttpStatus.OK);
    }

    public static ResponseEntity<?> ok(AppStatus appStatus, String redirect) {
        return new ResponseEntity<>(Rest.Response.ok(appStatus, redirect), HttpStatus.OK);
    }

    public static ResponseEntity<?> fail(AppStatus appStatus) {
        return fail(appStatus, "");
    }

    public static ResponseEntity<?> fail(AppStatus appStatus, String redirect) {
        return new ResponseEntity<>(Rest.Response.fail(appStatus, redirect), getStatus(appStatus));
    }

    public static ResponseEntity<?> fail(AppStatus appStatus, Map<String, String> fieldErrors) {
        return new ResponseEntity<>(Rest.Response.fail(appStatus, fieldErrors), getStatus(appStatus));
    }

    public static ResponseEntity<?> fail(AppStatus appStatus, String errorField, String errorMessage) {
        return new ResponseEntity<>(Rest.Response.fail(appStatus, Map.of(errorField, errorMessage)), getStatus(appStatus));
    }


    // AppStatus 내 상태코드 확인 후 대응하는 HttpStatus 반환
    private static HttpStatus getStatus(AppStatus appStatus) {

        return switch (appStatus.getHttpCode()) {
            case 200 -> HttpStatus.OK;
            case 400 -> HttpStatus.BAD_REQUEST;
            case 401 -> HttpStatus.UNAUTHORIZED;
            case 403 -> HttpStatus.FORBIDDEN;
            case 404 -> HttpStatus.NOT_FOUND;
            case 500 -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
