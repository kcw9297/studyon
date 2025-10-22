package studyon.app.layer.domain.payment_refund;

import lombok.*;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * 환불 관련 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class PaymentRefundDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long orderRefundId;
        private String refundReason;
        private Double refundPrice;
        private boolean isRefunded;
        private LocalDateTime createdAt;
        private Long paymentId;
        private Long paymentDetailId;
    }

    /** 등록용 DTO (환불 요청 시) */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private String refundReason;
        private Double refundPrice;
        private Long paymentId;
        private Long paymentDetailId;
    }

    /** 수정용 DTO (관리자 승인/처리 시) */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long orderRefundId;
        private boolean isRefunded;
    }
}
