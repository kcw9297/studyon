package studyon.app.layer.domain.payment_refund;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 환불 관련 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor
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

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createdAt;
    }
}
