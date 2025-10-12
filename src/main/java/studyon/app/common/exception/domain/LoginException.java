package studyon.app.common.exception.domain;

import studyon.app.common.exception.domain.parent.ServiceException;

public class LoginException extends ServiceException {

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
