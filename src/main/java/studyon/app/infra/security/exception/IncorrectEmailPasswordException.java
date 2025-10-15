package studyon.app.infra.security.exception;

import org.springframework.security.core.AuthenticationException;

public class IncorrectEmailPasswordException extends AuthenticationException {

    public IncorrectEmailPasswordException(String msg) {
        super(msg);
    }

    public IncorrectEmailPasswordException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
