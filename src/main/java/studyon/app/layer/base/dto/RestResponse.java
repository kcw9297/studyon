package studyon.app.layer.base.dto;

import lombok.Getter;
import lombok.ToString;


/**
 * REST API 응답 객체
 * @version 1.0
 * @author kcw97
 */
@Getter
@ToString
public class RestResponse {

    private Boolean success;
    private String message;
    private String redirect;
    private Integer code;
    private Object data;

    private RestResponse(Boolean success, String message, String redirect, Integer code, Object data) {
        this.success = success;
        this.message = message;
        this.redirect = redirect;
        this.code = code;
        this.data = data;
    }

    public static RestResponse ok() {
        return ok(null);
    }

    public static RestResponse ok(String redirect) {
        return ok(null, redirect);
    }

    public static RestResponse ok(Object data) {
        return ok(null, data);
    }

    public static RestResponse ok(String message, Object data) {
        return ok(message, null, data);
    }

    public static RestResponse ok(String message, String redirect) {
        return ok(message, redirect, null);
    }

    public static RestResponse ok(String message, String redirect, Object data) {
        return new RestResponse(true, message, redirect, 0, data);
    }

    public static RestResponse fail(Integer code) {
        return fail(null, code);
    }

    public static RestResponse fail(String message, Integer code) {
        return fail(message, null, code);
    }

    public static RestResponse fail(String message, String redirect, Integer code) {
        return new RestResponse(false, message, redirect, code, null);
    }
}
