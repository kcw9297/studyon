package studyon.app.common.exception.common;

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
