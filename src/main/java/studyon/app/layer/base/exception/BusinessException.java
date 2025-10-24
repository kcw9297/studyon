package studyon.app.layer.base.exception;

import lombok.Getter;

/**
 * 비즈니스 로직 예외
 * <br> {@link studyon.app.common.constant.StatusCode} 코드 값을 담아 처리
 * @version 1.0
 * @author kcw97
 */

@Getter
public class BusinessException extends RuntimeException {

    private int statusCode;

    public BusinessException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
