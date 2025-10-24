package studyon.app.layer.base.dto;

import lombok.*;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.utils.StrUtils;

import java.util.Map;

/**
 * REST API 응답을 위한 DTO
 * @version 1.0
 * @author kcw97
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Rest {

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Response {

        private Boolean success;
        private Integer statusCode;
        private String message;
        private String redirect;
        private Object data;
        private Map<String, String> fieldErrors; // Bean Validation 실패 시 전달 정보


        public static Response ok() {
            return ok(AppStatus.OK, "");
        }

        public static Response ok(String redirect) {
            return ok(AppStatus.OK, redirect);
        }

        public static Response ok(AppStatus appStatus) {
            return ok(appStatus, "");
        }

        public static Response ok(AppStatus appStatus, Object data) {
            return ok(appStatus, "", data);
        }

        public static Response ok(AppStatus appStatus, String redirect) {
            return ok(appStatus, redirect, null);
        }

        public static Response ok(AppStatus appStatus, String redirect, Object data) {
            return new Response(true, appStatus.getHttpCode(), appStatus.getMessage(), redirect, data, null);
        }

        public static Response fail(AppStatus appStatus) {
            return fail(appStatus, "");
        }

        public static Response fail(AppStatus appStatus, String redirect) {
            return new Response(false, appStatus.getHttpCode(), appStatus.getMessage(), redirect, null, null);
        }

        public static Response fail(AppStatus appStatus, Map<String, String> fieldErrors) {
            return new Response(false, appStatus.getHttpCode(), appStatus.getMessage(), "", null, fieldErrors);
        }

        public static Response fail(AppStatus appStatus, String errorField, String errorMessage) {
            return new Response(false, appStatus.getHttpCode(), appStatus.getMessage(), "", null, Map.of(errorField, errorMessage));
        }
    }


}
