package studyon.app.layer.base.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import studyon.app.common.constant.Url;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.base.utils.ViewUtils;


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
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(value = BusinessLogicException.class)
    public String handleBusinessLogicEx(BusinessLogicException e) {
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
