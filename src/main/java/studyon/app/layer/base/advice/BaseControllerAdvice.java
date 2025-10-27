package studyon.app.layer.base.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.base.utils.ViewUtils;

import java.util.Objects;


/**
 * {@code @Controller} 컨트롤러 클래스 전역의 예외를 처리하는 어드바이스 클래스
 * @version 1.0
 * @author kcw97
 */

@Order // 가장 후순위
@Slf4j
@ControllerAdvice(annotations = Controller.class) // @RestController 를 더 우선적으로 처리하도록 해야 함
public class BaseControllerAdvice {

    /**
     * 필요한 파라미터가 존재하지 않는 경우 예외 처리
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public String handleBeanValidationEx(ConstraintViolationException e) {
        return ViewUtils.return403();
    }


    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(value = BusinessLogicException.class)
    public String handleBusinessLogicEx(BusinessLogicException e) {

        if (Objects.equals(e.getAppStatus(), AppStatus.AUTH_INVALID_REQUEST))
            return ViewUtils.return403();

        return ViewUtils.redirectHome();
    }

    /**
     * 예기치 않은 예외 처리
     */
    @ExceptionHandler(value = Exception.class)
    public String handleOtherEx(Exception e) {
        log.error("예상 외 예외 발생! 확인 요망! 오류 메세지 : {}\n", e.getMessage(), e);
        return ViewUtils.redirectHome();
    }

}
