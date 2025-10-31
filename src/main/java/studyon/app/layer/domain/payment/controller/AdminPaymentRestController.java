package studyon.app.layer.domain.payment.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.validation.annotation.Title;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.PaymentSession;
import studyon.app.layer.domain.payment.service.PaymentService;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-29) : khs97 최초 작성
 */

/**
 * 강의 결제 처리 요청을 받는 관리자 컨트롤러 클래스
 * @version 1.0
 * @author khs97
 */

@Slf4j
@RestController
@RequestMapping(Url.PAYMENTS_ADMIN_API)
@RequiredArgsConstructor
@Validated
public class AdminPaymentRestController {

    private final PaymentService paymentService;

    /**
     * [GET] 페이징 검색
     */
    @GetMapping
    public ResponseEntity<?> readPagedList(HttpSession session, PaymentDTO.Search rq, Page.Request prq) {

        // [1]  관리자용 검색 설정
        rq.setAdminSearch();

        // [2] 검색 수행
        Page.Response<PaymentDTO.Read> page = paymentService.readPagedList(rq, prq);

        // [3] 페이징 결과 반환
        return RestUtils.ok(page);
    }

    /**
     * [GET] 단일 조회
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<?> read(HttpSession session, @PathVariable Long paymentId) {

        // [1] 회원 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 조회 수행 (관리자 API가 아니면 본인만 정보 제공)
        PaymentDTO.Read data = paymentService.read(paymentId, profile);

        // [3] 조회 결과 반환
        return RestUtils.ok(data);
    }


    /**
     * [PATCH] 실제 결제 전 검증 (유효성 검사는 생략) - 환불
     * <br>유효성 검증, 로그인 및 권한 검증, 주문세션 검증, 강의 구매가능 상태 검증 등
     */
    @PatchMapping("/{paymentId}/refund")
    public ResponseEntity<?> refund(HttpSession session,
                                    @PathVariable Long paymentId,
                                    @Title(max=15) String refundReason) {
        log.info("[PATCH] 환불 요청: paymentId={}, refundReason='{}'", paymentId, refundReason);
        // [1] 환불 수행 (현재는 단 하나씩만 주문하기 때문에, 전액 환불)
        paymentService.refund(paymentId, refundReason);

        // [2] 환불 성공요청 반환
        return RestUtils.ok();
    }


    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPaymentPdf(PaymentDTO.Search rq) {
        byte[] pdfBytes = paymentService.generatePaymentListPdf(rq);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payment_list.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }


}
