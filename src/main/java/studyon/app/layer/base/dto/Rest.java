package studyon.app.layer.base.dto;

import lombok.*;
import studyon.app.common.enums.AppStatus;

import java.util.Map;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 정적 메소드 버그 수정 및 파라미터 변경
 */

/**
 * REST API 응답을 위한 DTO
 * @version 1.1
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
        private Map<String, String> inputErrors; // Bean Validation 실패 시 전달 정보


        public static Response ok() {
            return ok(AppStatus.OK, "");
        }

        public static Response ok(String redirect) {
            return ok(AppStatus.OK, redirect);
        }

        public static Response ok(AppStatus appStatus) {
            return ok(appStatus, "");
        }

        public static Response ok(Object data, AppStatus appStatus) {
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

        public static Response fail(AppStatus appStatus, Map<String, String> inputErrors) {
            return new Response(false, appStatus.getHttpCode(), appStatus.getMessage(), "", null, inputErrors);
        }

        public static Response fail(AppStatus appStatus, String errorField, String errorMessage) {
            return new Response(false, appStatus.getHttpCode(), appStatus.getMessage(), "", null, Map.of(errorField, errorMessage));
        }
    }


}
