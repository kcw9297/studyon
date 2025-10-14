package studyon.app.common.exception.security;

import org.springframework.security.core.AuthenticationException;

public class IncorrectEmailPasswordException extends AuthenticationException {

    public IncorrectEmailPasswordException(String msg) {
        super(msg);
    }

    public IncorrectEmailPasswordException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
