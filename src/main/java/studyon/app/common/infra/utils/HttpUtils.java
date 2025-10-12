package studyon.app.common.infra.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import studyon.app.common.dto.Result;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {


    public static HttpSession getSession(HttpServletRequest request) {
        return getSession(request, false);
    }

    public static HttpSession getSession(HttpServletRequest request, boolean create) {
        return request.getSession(create);
    }

    public static String getSessionId(HttpServletRequest request) {
        return getSessionId(request, false);
    }

    public static String getSessionId(HttpServletRequest request, boolean create) {
        return getSession(request, create).getId();
    }


    public static ResponseEntity<?> ok(String message, Object data) {
        return new ResponseEntity<>(Result.ok(message, data), HttpStatus.OK);
    }

    public static ResponseEntity<?> ok(String message, String redirect) {
        return new ResponseEntity<>(Result.ok(message, redirect), HttpStatus.OK);
    }

    public static ResponseEntity<?> fail400(String message) {
        return new ResponseEntity<>(Result.fail(message, 1), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> fail400(String message, String redirect) {
        return new ResponseEntity<>(Result.fail(message, redirect, 1), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> fail500(String message) {
        return new ResponseEntity<>(Result.fail(message, -1), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<?> fail500(String message, String redirect) {
        return new ResponseEntity<>(Result.fail(message, redirect, -1), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
