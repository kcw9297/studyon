package studyon.app.infra.security.exception;

import org.springframework.security.core.AuthenticationException;

public class WithdrawalException extends AuthenticationException {

    public WithdrawalException(String msg) {
        super(msg);
    }

    public WithdrawalException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
