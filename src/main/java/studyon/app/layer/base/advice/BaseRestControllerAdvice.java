package studyon.app.layer.base.advice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.common.exception.PaymentException;
import studyon.app.layer.base.utils.RestUtils;

import java.util.Map;
import java.util.Objects;
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


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleBeanValidationEx(MethodArgumentNotValidException e) {

        // [1] 발생 오류 추출
        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Objects.isNull(fieldError.getDefaultMessage()) ? "올바른 값을 입력해 주세요." : fieldError.getDefaultMessage(),
                        (msg1, msg2) -> msg1 // 중복 시 첫 번째 유지
                ));

        // [2] 유효성 검사 실패 응답 반환
        log.error(errors.toString());
        return RestUtils.fail(AppStatus.VALIDATION_INVALID_PARAMETER, errors);
    }


    /**
     * 결제 예외 처리
     * <br>결제 실패 + 환불 실패 복합 예외 처리
     */
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(PaymentException e) {

        // [1] 예외 원인 분석
        Exception paymentEx = e.getPaymentFailureCause(); // 결제 예외
        Exception refundEx = e.getRefundFailureCause(); // 환불 예외

        // [2] 환불 실패가 있는 경우 (이중 예외)와 구분하여 예외 응답 반환
        if (Objects.nonNull(refundEx)) {
            log.error("⚠️ 긴급: 수동 환불 처리 필요! {}", refundEx.getMessage());
            return RestUtils.fail(e.getMessage(), e.getStatusCode()); // 수동으로 전파한 메세지, 코드 반환

            // 결제만 실패하고 환불은 성공한 경우 (결제 예외만 실패해서 PaymentException 으로 덮어씌워진 경우)
        } else {

            if (paymentEx instanceof BusinessLogicException businessEx)
                return RestUtils.fail(businessEx.getAppStatus());

            else return RestUtils.fail(AppStatus.SERVER_ERROR);
        }
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
