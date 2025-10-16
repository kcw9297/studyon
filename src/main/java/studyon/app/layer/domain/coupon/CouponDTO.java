package studyon.app.layer.domain.coupon;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * 쿠폰 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor
public class CouponDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long couponId;
        private String name;
        private Double discountPrice;
        private Double discountRate;
        private LocalDateTime validFrom;
        private LocalDateTime validUntil;
        private Double discountLimit;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private String name;
        private Double discountPrice;
        private Double discountRate;
        private LocalDateTime validFrom;
        private LocalDateTime validUntil;
        private Double discountLimit;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private String name;
        private Double discountPrice;
        private Double discountRate;
        private LocalDateTime validFrom;
        private LocalDateTime validUntil;
        private Double discountLimit;
    }
}
