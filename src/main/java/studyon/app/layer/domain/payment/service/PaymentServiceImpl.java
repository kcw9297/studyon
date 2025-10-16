package studyon.app.layer.domain.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.repository.PaymentRepository;

import java.util.List;

/**
 * 결제 서비스 인터페이스 구현체
 * @version 1.0
 * @author khj00
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;

    @Override
    public PaymentDTO.Read createPayment(PaymentDTO.Write rq) {
        return null;
    }

    @Override
    public Page.Response<PaymentDTO.Read> getPaymentsByMemberId(Long memberId) {
        return null;
    }

    @Override
    public PaymentDTO.Read getPaymentDetail(Long paymentId) {
        return null;
    }
}
