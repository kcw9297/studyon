package studyon.app.layer.domain.payment_details.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.repository.PaymentRepository;
import studyon.app.layer.domain.payment_details.PaymentDetails;
import studyon.app.layer.domain.payment_details.PaymentDetailsDTO;
import studyon.app.layer.domain.payment_details.repository.PaymentDetailRepository;

import java.util.Optional;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 결제 상세 서비스 구현체
 * @version 1.0
 * @author khj00
 */

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentDetailServiceImpl implements PaymentDetailService {

    private final PaymentDetailRepository paymentDetailRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentDetailsDTO.Read createPaymentDetails(Long paymentId, String apiResponse) {
        /*

        // [1] 결제 정보 존재 여부 확인
        PaymentDTO.Read payment = paymentRepository.findById(payment);
        // [2] PaymentDetails 생성 및 저장
        PaymentDetails saved = paymentDetailRepository.save(
                PaymentDetails.builder()
                        .payment(payment)
                        .paymentApiResult(apiResponse)
                        .build()
                );
         */
        return null;
    }
    /**
     * 결제 ID로 상세 내역 조회
     * @param paymentId 결제 ID
     * @return PaymentDetails(Optional)
     */
    @Override
    @Transactional
    public Optional<PaymentDetailsDTO.Read> getPaymentDetailsByPaymentId(Long paymentId) {
        // [1] 결제 ID로 결제 상세 정보 가져오기
        return paymentDetailRepository.findByPaymentId(paymentId)
                .map(DTOMapper::toReadDTO);
    }
    /**
     * 결제 상세 내역 삭제
     * @param paymentId 결제 ID
     */
    @Override
    public void deletePaymentDetails(Long paymentId) {
        // [1] ID로 결제 정보 가져와서 삭제
        paymentDetailRepository.findByPaymentId(paymentId)
                .ifPresent(paymentDetailRepository::delete);
    }
}
