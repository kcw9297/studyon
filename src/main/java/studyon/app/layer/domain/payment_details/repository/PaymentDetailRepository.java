package studyon.app.layer.domain.payment_details.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment_details.PaymentDetails;

import java.util.Optional;

/**
 * 결제 상세 레포지토리
 * @version 1.0
 * @author khj00
 */

public interface PaymentDetailRepository extends JpaRepository<PaymentDetails, Long> {
    // 특정 결제 ID로 PaymentDetails 조회
    @Query("SELECT d FROM PaymentDetails d WHERE d.payment.paymentId = :paymentId")
    Optional<PaymentDetails> findByPaymentId(Long paymentId);
}
