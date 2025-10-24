package studyon.app.common.exception;

import lombok.Getter;
import studyon.app.common.enums.AppStatus;

import java.util.Map;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-23) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 최상위 커스텀 예외를 상속하는 것으로 변경
 */

/**
 * 유효성 검사 예외 (= BeanValidation)
 * @version 1.1
 * @author kcw97
 */

@Getter
public class ValidationException extends AppException {

    public ValidationException(AppStatus appStatus, Map<String, String> errors) {
        super(appStatus, errors);
    }
}
