package studyon.app.common.exception;

import lombok.Getter;

/**
 * 결제 및 결제 실패로 인한 환불 로직 예외
 * @version 1.0
 * @author kcw97
 */

@Getter
public class PaymentException extends BusinessLogicException {

    // 결제/환불 시 발생한 예외
    private final Exception paymentFailureCause;
    private final Exception refundFailureCause;

    /**
     * 결제 검증 실패 -> 환불 보상로직 실패 으로 인한 이중 예외 발생 시 전파
     * @param paymentFailureCause 결제 실패 원인
     * @param refundFailureCause 환불 실패 원인
     * @param message 사용자에게 전달할 메시지
     * @param statusCode HTTP 상태 코드
     */
    public PaymentException(Exception paymentFailureCause, Exception refundFailureCause, String message, int statusCode) {
        super(message, statusCode);
        this.paymentFailureCause = paymentFailureCause;
        this.refundFailureCause = refundFailureCause;
    }


    /**
     * 결제 검증은 실패했으나, 환불 보상로직은 성공한 경우
     * @param paymentFailureCause 결제 실패 원인
     * @param message 사용자에게 전달할 메시지
     * @param statusCode HTTP 상태 코드
     */
    public PaymentException(Exception paymentFailureCause, String message, int statusCode) {
        super(message, statusCode);
        this.paymentFailureCause = paymentFailureCause;
        this.refundFailureCause = null;
    }

}
