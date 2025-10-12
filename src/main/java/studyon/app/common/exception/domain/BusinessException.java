package studyon.app.common.exception.domain;

import lombok.Getter;
import studyon.app.common.exception.domain.parent.ServiceException;

import java.util.Map;

@Getter
public class BusinessException extends ServiceException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
