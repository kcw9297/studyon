package studyon.app.layer.base.exception;

import studyon.app.layer.base.exception.parent.ServiceException;

public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
