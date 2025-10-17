package studyon.app.layer.base.exception;

import lombok.Getter;
import studyon.app.layer.base.exception.parent.ServiceException;

@Getter
public class BusinessException extends ServiceException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
