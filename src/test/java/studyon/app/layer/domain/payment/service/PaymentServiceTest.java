package studyon.app.layer.domain.payment.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.repository.PaymentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LectureRepository lectureRepository;

    @Test
    @DisplayName("결제 생성 테스트")
    void testCreatePayment() {
        // given
        Member member = memberRepository.findAll().get(0);
        Lecture lecture = lectureRepository.findAll().get(0);

        PaymentDTO.Write rq = PaymentDTO.Write.builder()
                .memberId(member.getMemberId())
                .lectureId(lecture.getLectureId())
                .originalPrice(lecture.getPrice())
                .discountPrice(0.0)
                .paidPrice(lecture.getPrice())
                .build();

        // when
        PaymentDTO.Read created = paymentService.createPayment(rq);

        // then
        assertThat(created).isNotNull();
        assertThat(created.getPaymentId()).isNotNull();
        assertThat(created.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(created.getLectureId()).isEqualTo(lecture.getLectureId());
    }

    @Test
    @DisplayName("회원별 결제 내역 조회 테스트")
    void testGetPaymentsByMemberId() {
        // given
        Long memberId = 4L;

        // when
        Page.Response<PaymentDTO.Read> response = paymentService.getPaymentsByMemberId(memberId);

        // then
        assertThat(response).isNotNull();                      // 응답 객체 존재 여부 확인
        assertThat(response.getData()).isNotEmpty();           // 데이터 리스트 비어있지 않은지
        assertThat(response.getData().get(0).getMemberId())    // 첫 번째 결제의 회원 ID 확인
                .isEqualTo(memberId);

        // 로그 출력 (테스트 콘솔 확인용)
        System.out.println("=== 회원 " + memberId + "의 결제 내역 ===");
        response.getData().forEach(dto -> {
            System.out.printf("결제ID: %d | 강의ID: %d | 결제금액: %.2f원 | 결제일: %s%n",
                    dto.getPaymentId(),
                    dto.getLectureId(),
                    dto.getPaidPrice(),
                    dto.getPaidAt());
        });

        System.out.println("총 결제 건수: " + response.getDataCount());
    }
}