package studyon.app.infra.mail.exception;

public class VerifyException extends RuntimeException {

    public VerifyException() {
        super();
    }

    public VerifyException(String message) {
        super(message);
    }

    public VerifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
