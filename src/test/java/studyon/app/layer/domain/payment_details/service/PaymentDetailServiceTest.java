package studyon.app.layer.domain.payment_details.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.common.enums.Difficulty;
import studyon.app.common.enums.Role;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.repository.PaymentRepository;
import studyon.app.layer.domain.payment_details.PaymentDetails;
import studyon.app.layer.domain.payment_details.PaymentDetailsDTO;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 결제 상세 서비스 테스트
 * @version 1.0
 * @author khj00
 */

@SpringBootTest
@Transactional
class PaymentDetailServiceTest {
    @Autowired
    private PaymentDetailService paymentDetailService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Test
    @DisplayName("결제 상세 생성 및 DTO 반환 테스트")
    void createPaymentDetails() {
        // given

        Member member = memberRepository.findById(4L)
                .orElseThrow(() -> new IllegalArgumentException("테스트용 회원 데이터 X"));

        Lecture lecture = lectureRepository.findById(3L)
                .orElseThrow(() -> new IllegalArgumentException("테스트용 강의 데이터 X"));

        Payment payment = paymentRepository.save(Payment.builder()
                .originalPrice(10000.0)
                .paidPrice(9000.0)
                .discountPrice(1000.0)
                .lecture(lecture)
                .member(member)
                .build());

        String fakeApiResponse = """
                {
                    "paymentId" : 12345,
                    "status" : SUCCESS,
                    "method" : CARD,
                    "transactionId" : "TX-001"
                }
            """;
        // when
        PaymentDetailsDTO.Read dto = paymentDetailService.createPaymentDetails(payment.getPaymentId()
                , fakeApiResponse);
        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getPaymentApiResult()).contains("SUCCESS");
        assertThat(dto.getPaymentId()).isEqualTo(payment.getPaymentId());

        System.out.println("DTO 반환 확인: " + dto);

    }

    @Test
    @DisplayName("결제 상세 조회 테스트")
    void getPaymentDetailsByPaymentId() {
        // given
        Member member = memberRepository.findAll().get(0);
        Lecture lecture = lectureRepository.findAll().get(0);

        Payment payment = paymentRepository.save(Payment.builder()
                .originalPrice(15000.0)
                .paidPrice(13000.0)
                .discountPrice(2000.0)
                .member(member)
                .lecture(lecture)
                .build());

        String apiJson = """
                {
                    "paymentId" : 99999,
                    "status" : "SUCCESS",
                    "method" : "CARD"
                }
            """;
        paymentDetailService.createPaymentDetails(payment.getPaymentId(), apiJson);

        // when
        Optional<PaymentDetailsDTO.Read> result =
                paymentDetailService.getPaymentDetailsByPaymentId(payment.getPaymentId());

        // then
        assertThat(result).isPresent();
        System.out.println("조회된 결제 상세: " + result.get());
    }

    @Test
    @DisplayName("결제 상세 내역 삭제 테스트")
    void deletePaymentDetails() {
        // given(준비)
        Member member = memberRepository.findAll().get(0);
        Lecture lecture = lectureRepository.findAll().get(0);

        Payment payment = paymentRepository.save(Payment.builder()
                .originalPrice(12000.0)
                .paidPrice(10000.0)
                .discountPrice(2000.0)
                .member(member)
                .lecture(lecture)
                .build()
        );

        String apiJson = """
                {
                    "paymentId": 22222,
                    "status": "SUCCESS",
                    "method": "CARD"
                }
                """;

        paymentDetailService.createPaymentDetails(payment.getPaymentId(), apiJson);

        // when(진행)
        paymentDetailService.deletePaymentDetails(payment.getPaymentId());
        Optional<PaymentDetailsDTO.Read> deleteResult =
                paymentDetailService.getPaymentDetailsByPaymentId(payment.getPaymentId());
        
        // then(검증)
        assertThat(deleteResult).isEmpty();
        System.out.println("결제 상세 내역 삭제 완료");
    }
}