package studyon.app.layer.domain.payment.service;

import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.payment.PaymentDTO;

import java.util.List;

/**
 * 결제 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */


public interface PaymentService {
    /**
     * 결제 생성 (등록)
     * @param dto 결제 요청 데이터
     * @return 생성된 Payment 엔티티
     */
    PaymentDTO.Read createPayment(PaymentDTO.Pay dto);

    /**
     * 특정 회원의 결제 목록 조회
     * @param memberId 회원 ID
     * @return 결제 리스트
     */
    Page.Response<PaymentDTO.Read> getPaymentsByMemberId(Long memberId);

    /**
     * 결제 상세 조회
     * @param paymentId 결제 ID
     * @return Payment 객체
     */
    PaymentDTO.Read getPaymentDetail(Long paymentId);

}
