package studyon.app.infra.mail.exception;

public class VerifyCodeException extends RuntimeException {

    public VerifyCodeException() {
        super();
    }

    public VerifyCodeException(String message) {
        super(message);
    }

    public VerifyCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
