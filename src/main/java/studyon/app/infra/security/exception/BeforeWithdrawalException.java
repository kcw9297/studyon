package studyon.app.infra.security.exception;

import org.springframework.security.core.AuthenticationException;

public class BeforeWithdrawalException extends AuthenticationException {

    public BeforeWithdrawalException(String msg) {
        super(msg);
    }

    public BeforeWithdrawalException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
