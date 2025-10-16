package studyon.app.layer.domain.payment.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.layer.domain.payment.Payment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("특정 회원의 강의 수강(구매) 내역 불러오기 테스트")
    void findPaymentByMemberId() {
        // given
        Long memberId = 8L;

        // when
        List<Payment> payments = paymentRepository.findByMember_MemberId(memberId);

        // then
        assertThat(payments).isNotEmpty();
        System.out.println("=== 회원 " + memberId + "의 수강 내역 목록 ===");
        payments.forEach(payment -> {
            System.out.println("결제 ID: " + payment.getPaymentId()
                    + " | 강의명: " + payment.getLecture().getTitle()
                    + " | 결제금액: " + payment.getPaidPrice()
                    + " | 결제일시: " + payment.getPaidAt());
        });

        // 추가 검증: 강의 중복 결제 없음
        assertThat(payments)
                .extracting(p -> p.getLecture().getLectureId())
                .doesNotHaveDuplicates();
    }
}