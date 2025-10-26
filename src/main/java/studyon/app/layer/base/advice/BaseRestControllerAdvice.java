package studyon.app.layer.base.advice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.base.utils.RestUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@code @RestController} 컨트롤러 클래스 전역의 예외를 처리하는 어드바이스 클래스
 * @version 1.0
 * @author kcw97
 */

@Order(1) // 우선순위 높음
@Slf4j
@RestControllerAdvice(annotations = RestController.class) // @RestController 만 처리
public class BaseRestControllerAdvice {

    /**
     * Bean Validation 유효성 검사 실패 예외 처리
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> handleBeanValidationEx(ConstraintViolationException e) {

        // [1] 발생 오류 추출
        Map<String, String> errors = e.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        v -> {
                            String[] parts = v.getPropertyPath().toString().split("\\.");
                            return parts[parts.length - 1]; // ✅ 가장 마지막 필드명
                        },
                        ConstraintViolation::getMessage,
                        (msg1, msg2) -> msg1 // 중복 필드 있을 경우 최초정보 유지
                ));

        // [2] 유효성 검사 실패 응답 반환
        log.error(errors.toString());
        return RestUtils.fail(AppStatus.VALIDATION_INVALID_PARAMETER, errors);
    }

    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(value = BusinessLogicException.class)
    public ResponseEntity<?> handleBusinessLogicEx(BusinessLogicException e) {
        return RestUtils.fail(e.getAppStatus());
    }

    /**
     * 예기치 않은 예외 처리 (바깥으로 예외 정보가 노출 방지)
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleOtherEx(Exception e) {
        log.error("예상 외 예외 발생! 확인 요망! 오류 메세지 : {}\n", e.getMessage(), e);
        return RestUtils.fail(AppStatus.SERVER_ERROR);
    }

}
