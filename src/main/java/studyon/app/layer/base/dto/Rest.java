package studyon.app.layer.base.dto;

import lombok.*;

import java.util.Map;

/**
 * REST API 응답을 위한 DTO
 * @version 1.0
 * @author kcw97
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Rest {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Message {

        private String title;
        private String content;
    }

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Response {

        private Boolean success;
        private Message message;
        private String redirect;
        private Integer code;
        private Object data;
        private Map<String, String> fieldErrors; // Bean Validation 실패 시 전달 정보


        public static Response ok() {
            return ok(null);
        }

        public static Response ok(String redirect) {
            return ok(null, redirect);
        }

        public static Response ok(Object data) {
            return ok(null, data);
        }

        public static Response ok(Message message, Object data) {
            return ok(message, null, data);
        }

        public static Response ok(Message message, String redirect) {
            return ok(message, redirect, null);
        }

        public static Response ok(Message message, String redirect, Object data) {
            return new Response(true, message, redirect, 0, data, null);
        }

        public static Response fail(Integer code) {
            return fail(code, null);
        }

        public static Response fail(Integer code, Message message) {
            return fail(code, message, null);
        }

        public static Response fail(Integer code, Message message, String redirect) {
            return new Response(false, message, redirect, code, null, null);
        }

        public static Response fail(Map<String, String> fieldErrors) {
            return new Response(false, null, null, -2, null, fieldErrors);
        }

        public static Response fail(String errorField, String errorMessage) {
            return new Response(false, null, null, -2, null, Map.of(errorField, errorMessage));
        }
    }


}
