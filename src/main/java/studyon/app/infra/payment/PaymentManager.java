package studyon.app.infra.payment;

/**
 * 주문 API 기반 카드결제를 돕는 클래스
 * @version 1.0
 * @author kcw97
 */
public interface PaymentManager {

    /**
     * 클라이언트에서 수행한 결제를 검증하는 메소드
     * @param paymentApiResult 결제 결과 API 문자열 (JSON 응답 문자열)
     * @return 결제 결과 상태코드 반환 {@link studyon.app.common.constant.PaymentCode}
     */
    int checkPayment(String paymentApiResult);

    /**
     * 현재 주문 전액 환불
     * @param paymentUid 주문 대행사에서 제공한 주문고유번호
     * @return 결제 결과 상태코드 반환 {@link studyon.app.common.constant.PaymentCode}
     */
    int refundAll(String paymentUid, String refundReason);

}
