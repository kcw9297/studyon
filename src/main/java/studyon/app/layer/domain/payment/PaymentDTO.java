package studyon.app.layer.domain.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import studyon.app.layer.base.validation.annotation.Name;
import studyon.app.layer.base.validation.annotation.NumberString;

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
        private Long memberId; // 관리자가 아닌 경우, 해당 회원의 목록만 조회
        private Boolean isAdmin; // 서버에서 삽입하는 값 (관리자 여부에 따라 다르게 조회)

        // 일반 학생 검색
        public void setStudentSearch(Long memberId) {
            this.memberId = memberId;
            this.isAdmin = false;
        }

        // 관리자 검색
        public void setAdminSearch() {
            this.memberId = null;
            this.isAdmin = true;
        }
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

        // 결제 시 제공되는 정보
        private String paymentUid;
        private Double paidAmount;
        private String paymentApiResult;
        private Long lectureId;
        private Long memberId; // 서버에서 조달

        // 결제 시에만 임시 활용 정보 (실제 엔티티 저장 시 미사용)
        private String token;

        @Name
        private String buyerName;

        @NumberString(min = 10, max = 20)
        private String buyerPhoneNumber;
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
