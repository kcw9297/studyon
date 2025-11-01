package studyon.app.layer.domain.payment.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return Page.Response.create(payments, prq.getPage(), prq.getSize(), count);
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
            //paymentManager.checkPayment(rq.getPaymentApiResult());

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

            // KHS 수정 LECUTRE 구매시 TOTAL STUDENT +1
            lecture.increaseTotalStudents();

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

        payment.refund();

        // [3] 환불 수행
        paymentManager.refundAll(payment.getPaymentUid(), refundReason);
    }


    @Override
    public byte[] generatePaymentListPdf(PaymentDTO.Search rq) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // [1] 모든 결제 데이터 조회 (페이징 제한 해제)
            List<PaymentDTO.Read> payments =
                    paymentMapper.selectAll(rq, new Page.Request(0, Integer.MAX_VALUE));

            // [2] PDF 문서 설정
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // [3] 폰트 설정
            BaseFont baseFont = BaseFont.createFont("fonts/malgun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font infoFont = new Font(baseFont, 11, Font.NORMAL);
            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            Font bodyFont = new Font(baseFont, 10, Font.NORMAL);

            // [4] 생성 시각 표시
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = LocalDateTime.now().format(formatter);

            // [5] 필터 상태 표시 (검색, 환불, 정렬 등)
            String filterLabel = switch (rq.getFilter() == null ? "" : rq.getFilter()) {
                case "paymentUid" -> "결제번호";
                case "lectureTitle" -> "강의명";
                case "nickname" -> "결제자";
                default -> "전체";
            };

            String refundLabel = switch (String.valueOf(rq.getIsRefunded())) {
                case "true" -> "환불완료";
                case "false" -> "결제완료";
                default -> "전체";
            };

            String orderLabel = switch (rq.getOrderBy() == null ? "" : rq.getOrderBy()) {
                case "date" -> "결제일순(최신)";
                case "amount" -> "금액순(높은 금액)";
                case "refund" -> "환불우선";
                default -> "기본정렬";
            };

            String keywordText = (rq.getKeyword() != null && !rq.getKeyword().isBlank())
                    ? rq.getKeyword()
                    : "없음";

            String filterSummary = String.format(
                    "필터: 검색=%s / 환불=%s / 정렬=%s / 키워드=%s",
                    filterLabel, refundLabel, orderLabel, keywordText
            );

            // [6] 제목 + 필터 정보 추가
            document.add(new Paragraph("💳 Study On 결제 내역 목록", titleFont));
            document.add(new Paragraph("생성시각: " + formattedDate, bodyFont));
            document.add(new Paragraph(filterSummary, infoFont));
            document.add(new Paragraph(" ")); // 줄 간격용

            // [7] 표 생성
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setWidths(new float[]{1f, 3f, 4f, 3f, 2.5f, 3f, 2.5f});

            // [8] 테이블 헤더
            String[] headers = {"No", "결제번호", "강의명", "결제자", "결제금액", "결제일", "환불상태"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPaddingTop(6f);
                cell.setPaddingBottom(6f);
                cell.setBackgroundColor(new BaseColor(230, 230, 230));
                table.addCell(cell);
            }

            // [9] 결제 데이터 행 추가
            int i = 1;
            for (PaymentDTO.Read p : payments) {
                table.addCell(centeredCell(String.valueOf(i++), bodyFont));
                table.addCell(centeredCell(p.getPaymentUid(), bodyFont));
                table.addCell(centeredCell(p.getLectureTitle(), bodyFont));
                table.addCell(centeredCell(p.getNickname(), bodyFont));
                table.addCell(centeredCell(
                        String.format("%,d원", p.getPaidAmount().longValue()), bodyFont
                ));
                table.addCell(centeredCell(
                        p.getCdate() != null ? p.getCdate().toLocalDate().toString() : "-", bodyFont
                ));
                table.addCell(centeredCell(p.getIsRefunded() ? "환불완료" : "결제완료", bodyFont));
            }

            // [10] PDF에 테이블 추가
            document.add(table);
            document.close();

            log.info("✅ [SERVICE] 결제 내역 PDF 생성 완료 ({}건)", payments.size());
            return out.toByteArray();

        } catch (IOException | DocumentException e) {
            throw new RuntimeException("결제 내역 PDF 생성 실패", e);
        }
    }

    // [공통 셀 정렬 메소드]
    private PdfPCell centeredCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "-", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(4f);
        return cell;
    }

}
