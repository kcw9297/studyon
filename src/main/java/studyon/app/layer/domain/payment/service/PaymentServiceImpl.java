package studyon.app.layer.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Role;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.common.exception.PaymentException;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.payment.PaymentManager;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.member_lecture.MemberLecture;
import studyon.app.layer.domain.member_lecture.repository.MemberLectureRepository;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.PaymentSession;
import studyon.app.layer.domain.payment.mapper.PaymentMapper;
import studyon.app.layer.domain.payment.repository.PaymentRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    private final PaymentMapper paymentMapper;
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;
    private final MemberLectureRepository memberLectureRepository;

    private final PaymentManager paymentManager;
    private final CacheManager cacheManager;


    @Override
    @Transactional(readOnly = true)
    public Page.Response<PaymentDTO.Read> readPagedList(PaymentDTO.Search rq, Page.Request prq) {

        // 결제 목록 조회
        List<PaymentDTO.Read> payments = paymentMapper.selectAll(rq, prq);

        // DTO 변환
        Integer count = paymentMapper.countAll(rq);

        // 페이지 응답 포맷 구성
        return Page.Response.create(payments, prq.getPage(), prq.getPage(), count);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO.Read> readRecentList(Duration days) {

        // [1] 날짜정보 변경 (JPA에서 날짜 정보를 직접 이용하기 복잡)
        LocalDateTime endDate = LocalDateTime.now().plus(days);

        // [2] 조회 및 매핑 후 반환
        return paymentRepository
                .findWithinDate(endDate)
                .stream()
                .map(DTOMapper::toReadDTO)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public PaymentDTO.Read read(Long paymentId, MemberProfile profile) {

        // [1] 결제정보 조회
        Payment payment = paymentRepository
                .findWithMemberById(paymentId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.PAYMENT_NOT_FOUND));

        // [2] 조회 가능여부 검증
        boolean isOwner = Objects.equals(profile.getMemberId(), payment.getMember().getMemberId());
        boolean isAdmin = Objects.equals(profile.getRole(), Role.ROLE_ADMIN);

        // 관리자도 아닌데, 주인도 아닌 경우 예외 반환
        if (!isAdmin && !isOwner) throw new BusinessLogicException(AppStatus.ACCESS_DENIED);
        return DTOMapper.toReadDTO(payment);
    }


    @Override
    @Transactional(readOnly = true)
    public PaymentSession access(Long memberId, Long lectureId) {

        // [1] 구매 회원 & 강의 정보 & 결제 정보 조회
        Member member = memberRepository
                .findByMemberIdAndIsActive(memberId, true) // 활성 상태의 회원만 조회
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));

        Lecture lecture = lectureRepository
                .findAllFetchByLectureIdAndOnSale(lectureId, true) // 판매 중인 강의만 조회
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        // (환불하지 않은) 결제 이력이 존재하면, 재구매가 불가능하므로 예외 반환
        boolean isExistPayment = paymentRepository.existsByMemberIdAndLectureIdAndIsRefunded(memberId, lectureId, false);
        if (isExistPayment) throw new BusinessLogicException(AppStatus.PAYMENT_ALREADY_PAYED);


        // [2] 토큰 정보 및 결제할 강의 정보가 포함된 세션 발급
        String token = StrUtils.createUUID();
        String thumbnailImagePath = Objects.isNull(lecture.getThumbnailFile()) ? null : lecture.getThumbnailFile().getFilePath();
        PaymentSession paymentSession = new PaymentSession(
                token, lectureId, thumbnailImagePath, lecture.getTitle(), lecture.getTeacher().getMember().getNickname(), lecture.getPrice()
        );

        // [3] 세션 정보 저장 후 반환
        cacheManager.recordPaymentRequest(memberId, lectureId, paymentSession);
        return paymentSession;
    }


    @Override
    @Transactional(readOnly = true)
    public PaymentSession verify(Long memberId, Long lectureId, String token) {

        // [1] 결제 세션 조회. 존재하지 않거나, 토큰 정보가 다르면 유효하지 않은 접근으로 판단
        PaymentSession paymentRequest = cacheManager.getPaymentRequest(memberId, lectureId, PaymentSession.class);
        log.warn("paymentRequest = {}", paymentRequest);
        if (Objects.isNull(paymentRequest) || !Objects.equals(paymentRequest.getToken(), token))
            throw new BusinessLogicException(AppStatus.PAYMENT_INVALID_REQUEST);

        // [2] 구매 회원 & 강의 정보 존재 & 구매여부 반환
        boolean isExistMember = memberRepository.existsByMemberIdAndIsActive(memberId, true);
        if (!isExistMember) throw new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND);

        boolean isExistLecture = lectureRepository.existsByLectureIdAndOnSale(lectureId, true);
        if (!isExistLecture) throw new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND);

        // (환불하지 않은) 결제 이력이 존재하면, 재구매가 불가능하므로 예외 반환
        boolean isExistPayment = paymentRepository.existsByMemberIdAndLectureIdAndIsRefunded(memberId, lectureId, false);
        if (isExistPayment) throw new BusinessLogicException(AppStatus.PAYMENT_ALREADY_PAYED);

        // [3] 검증 통과 시, 결제 세션정보 반환
        return paymentRequest;
    }


    @Override
    public PaymentDTO.Read pay(PaymentDTO.Pay rq) {

        try {

            // [1] 결제 세션 조회. 존재하지 않거나, 토큰 정보가 다르면 유효하지 않은 접근으로 판단
            // 이를 검증하지 않으면 한 번만 구매 가능한 강의를 중복 구매처리될 수 있음
            PaymentSession paymentRequest =
                    cacheManager.getAndDeletePaymentRequest(rq.getMemberId(), rq.getLectureId(), PaymentSession.class);

            if (Objects.isNull(paymentRequest) || !Objects.equals(paymentRequest.getToken(), rq.getToken()))
                throw new BusinessLogicException(AppStatus.PAYMENT_INVALID_REQUEST);


            // [1] 결제 검증 수행 (결제 결과가 조작되었거나, 다른 이유로 실패하면 예외 반환)
            paymentManager.checkPayment(rq.getPaymentApiResult());

            // [2] 구매 회원 & 강의 정보 조회
            Member member = memberRepository
                    .findByMemberIdAndIsActive(rq.getMemberId(), true) // 활성 상태의 회원만 조회
                    .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));

            Lecture lecture = lectureRepository
                    .findByLectureIdAndOnSale(rq.getLectureId(), true) // 판매 중인 강의만 조회
                    .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

            // (환불하지 않은) 결제 이력이 존재하면, 재구매가 불가능하므로 예외 반환
            boolean isExistPayment = paymentRepository.existsByMemberIdAndLectureIdAndIsRefunded(rq.getMemberId(), rq.getLectureId(), false);
            if (isExistPayment) throw new BusinessLogicException(AppStatus.PAYMENT_ALREADY_PAYED);

            // [3] 결제 및 회원강의 엔티티 생성
            Payment savedPayment = paymentRepository.save(DTOMapper.toEntity(rq, member, lecture));
            memberLectureRepository.save(new MemberLecture(member, lecture));

            // [4] 로그 기록을 위한 정보 저장 후 반환
            return DTOMapper.toReadDTO(savedPayment);

        } catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "결제 로직 실패! 환불 로직 수행. 원인 : %s".formatted(e.getMessage())));
            refundCauseByPaymentFail(e, rq);
            throw new PaymentException(e, e.getMessage(), 500);
        }
    }

    // 결제 검증 실패로 인한 환불 처리
    private void refundCauseByPaymentFail(Exception paymentEx, PaymentDTO.Pay rq) {

        try {
            paymentManager.refundAll(rq.getPaymentUid(), "결제 실패로 인한 환불");
            log.error(StrUtils.createLogStr(this.getClass(), "자동 환불 완료: paymentUid=%s".formatted(rq.getPaymentUid())));

        } catch (Exception refundEx) {

            // [1] 직접 오류상황 전파를 위한 메세지 생성
            log.error(StrUtils.createLogStr(this.getClass(), "결제 실패에 의한 환불 로직 실패!. 원인 : %s".formatted(refundEx.getMessage())));
            String message = String.format(
                    "결제에 실패하여 환불을 시도했으나 실패했습니다.\n" +
                            "결제번호와 함께 관리자에게 문의하세요.\n" +
                            "결제 실패 원인: %s\n" +
                            "환불 실패 원인: %s\n" +
                            "결제 번호: %s",
                    paymentEx.getMessage(),  // 결제 실패 원인I
                    refundEx.getMessage(),   // 환불 실패 원인
                    rq.getPaymentUid()
            );

            // [2] 예외 던지기
            throw new PaymentException(paymentEx, refundEx, message, 500);
        }
    }


    @Override
    public void refund(Long paymentId, String refundReason) {

        // [1] 결제 정보 조회
        Payment payment = paymentRepository
                .findById(paymentId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.PAYMENT_NOT_FOUND));

        // [2] 환불 검증
        if (payment.getIsRefunded()) // 이미 환불된 경우
            throw new BusinessLogicException(AppStatus.PAYMENT_ALREADY_REFUNDED);

        // 결제일로부터 정해진 환불 기간이 지난 경우 (PG사에선 보통 1년. 테스트용으로 1달)
        if (payment.getRefundedAt().plusMonths(1).isAfter(LocalDateTime.now()))
            throw new BusinessLogicException(AppStatus.PAYMENT_REFUND_NOT_AVAILABLE);


        // [3] 환불 수행
        paymentManager.refundAll(payment.getPaymentUid(), refundReason);
    }


}
