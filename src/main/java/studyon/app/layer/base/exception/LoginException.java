package studyon.app.layer.base.exception;

import studyon.app.layer.base.exception.parent.ServiceException;

public class LoginException extends ServiceException {

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
