package studyon.app.layer.base.exception;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 유효성 검사 예외 (= BeanValidation)
 * @version 1.0
 * @author kcw97
 */

@Getter
public class ValidationException extends RuntimeException {

    // view field error (ex. 이메일 형식 오류 등)
    private Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        this.errors = errors;
    }
}
