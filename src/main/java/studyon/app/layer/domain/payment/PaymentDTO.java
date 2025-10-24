package studyon.app.layer.domain.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import studyon.app.common.enums.Entity;
import studyon.app.infra.aop.LogInfo;

import java.time.LocalDateTime;
import java.util.Map;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * 결제 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor
public class PaymentDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {

        private Long paymentId;

        private String paymentUid;

        private Long lectureId;

        private String lectureTitle;

        private String nickname;

        @JsonFormat(pattern = "#,###")
        private Double paidAmount;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime cdate;

        private Boolean isRefunded;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime refundedAt;


    }

    @Data
    @Builder
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Pay extends LogInfo {

        private String paymentUid;
        private Double paidAmount;
        private String paymentMethod;
        private String paymentApiResult;
        private Long memberId;
        private Long lectureId;
    }

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Refund extends LogInfo {

        private Long paymentId;
        private String refundReason;

        @Builder
        public Refund(Long paymentId, String refundReason) {
            super(paymentId, Entity.PAYMENT);
            this.paymentId = paymentId;
            this.refundReason = refundReason;
        }
    }
}
