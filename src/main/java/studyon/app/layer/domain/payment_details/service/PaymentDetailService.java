package studyon.app.layer.domain.payment_details.service;

import studyon.app.layer.domain.payment_details.PaymentDetailsDTO;

import java.util.Optional;

public interface PaymentDetailService {
    /**
     * 결제 상세 내역 생성 (API 응답 저장)
     * @param paymentId 결제 ID
     * @param apiResponse 결제 API 응답(JSON)
     * @return 생성된 PaymentDetails 엔티티
     */
    PaymentDetailsDTO.Read createPaymentDetails(Long paymentId, String apiResponse);

    /**
     * 결제 ID로 상세 내역 조회
     * @param paymentId 결제 ID
     * @return PaymentDetails(Optional)
     */
    Optional<PaymentDetailsDTO.Read> getPaymentDetailsByPaymentId(Long paymentId);

    /**
     * 결제 상세 내역 삭제
     * @param paymentId 결제 ID
     */
    void deletePaymentDetails(Long paymentId);
}
