package studyon.app.layer.domain.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-28) : kcw97 검색용 DTO 추가
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
    public static class Search {

        private String filter; // 결제번호, 강의명, 강사명
        private String keyword;
        private Boolean isRefunded;
        private String orderBy;
    }



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
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Pay {

        private String paymentUid;
        private Double paidAmount;
        private String paymentApiResult;
        private Long memberId;
        private Long lectureId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Refund {

        private Long paymentId;
        private String refundReason;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeacherSales {
        private Long teacherId;
        private String nickname;
        private Double totalSales;
    }
}
