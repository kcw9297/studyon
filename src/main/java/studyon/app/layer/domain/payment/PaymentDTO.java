package studyon.app.layer.domain.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

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

        private Long lectureId;

        private String lectureTitle;

        private String nickname;

        private Long paymentId;

        @JsonFormat(pattern = "#,###")
        private Double paidPrice;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime cdate;

        private Boolean isRefunded;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime refundedAt;


    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {

        private Double paidPrice;
        private String paymentApiResult;
        private Long memberId;
        private Long lectureId;
    }
}
