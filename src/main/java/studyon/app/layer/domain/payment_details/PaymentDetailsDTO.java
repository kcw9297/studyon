package studyon.app.layer.domain.payment_details;

import lombok.*;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * 결제 상세 정보 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class PaymentDetailsDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long paymentDetailId;
        private String paymentApiResult;  // json
        private Long paymentId;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private String paymentApiResult;
        private Long paymentId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long paymentDetailId;
        private String paymentApiResult;
    }
}
