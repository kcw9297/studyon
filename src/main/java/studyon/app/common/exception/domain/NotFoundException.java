package studyon.app.common.exception.domain;

import studyon.app.common.exception.domain.parent.ServiceException;

public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
