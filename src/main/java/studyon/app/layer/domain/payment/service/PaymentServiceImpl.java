package studyon.app.layer.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.MemberDTO;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;

    @Override
    public PaymentDTO.Read createPayment(PaymentDTO.Write rq) {
        // 필요 엔티티들 조회
        Member member = memberRepository.findById(rq.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Lecture lecture = lectureRepository.findById(rq.getLectureId())
                .orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));

        // 결제 엔티티 생성
        Payment payment = DTOMapper.toEntity(rq, member, lecture);

        Payment savedPayment = paymentRepository.save(payment);

        return DTOMapper.toReadDTO(savedPayment);
    }

    @Override
    public Page.Response<PaymentDTO.Read> getPaymentsByMemberId(Long memberId) {
        // 결제 목록 조회
        List<Payment> payments = paymentRepository.findByMember_MemberId(memberId);

        // DTO 변환
        List<PaymentDTO.Read> dtoList = payments.stream()
                .map(DTOMapper::toReadDTO)
                .toList();

        // 페이지 응답 포맷 구성
        return Page.Response.create(dtoList, 0, 10, dtoList.size());
        // readPagedList -> 작명
    }

    @Override
    public PaymentDTO.Read getPaymentDetail(Long paymentId) {
        return null;
    }
}
