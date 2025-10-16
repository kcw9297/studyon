package studyon.app.layer.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.payment.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMember_MemberId(Long memberId);
}
