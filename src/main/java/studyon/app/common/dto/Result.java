package studyon.app.common.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Result {

    private Boolean success;
    private String message;
    private String redirect;
    private Integer code;
    private Object data;

    private Result(Boolean success, String message, String redirect, Integer code, Object data) {
        this.success = success;
        this.message = message;
        this.redirect = redirect;
        this.code = code;
        this.data = data;
    }

    public static Result ok() {
        return ok(null);
    }

    public static Result ok(String redirect) {
        return ok(null, redirect);
    }

    public static Result ok(Object data) {
        return ok(null, data);
    }

    public static Result ok(String message, Object data) {
        return ok(message, null, data);
    }

    public static Result ok(String message, String redirect) {
        return ok(message, redirect, null);
    }

    public static Result ok(String message, String redirect, Object data) {
        return new Result(true, message, redirect, 0, data);
    }

    public static Result fail(Integer code) {
        return fail(null, code);
    }

    public static Result fail(String message, Integer code) {
        return fail(message, null, code);
    }

    public static Result fail(String message, String redirect, Integer code) {
        return new Result(false, message, redirect, code, null);
    }
}
