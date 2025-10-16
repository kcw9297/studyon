package studyon.app.layer.domain.member_coupon;

import lombok.*;
import studyon.app.common.enums.CouponState;
import studyon.app.layer.domain.member.MemberDTO;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */


/**
 * 멤버별 쿠폰 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor
public class MemberCouponDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long memberCouponId;
        private LocalDateTime issuedAt;
        private LocalDateTime usedAt;
        private CouponState couponState;
        private Long couponId;
        private Long memberId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private Long couponId; // 어떤 쿠폰을 발급할지
        private Long memberId; // 어떤 회원에게 줄지
        private CouponState couponState;  // default == ISSUED
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long memberCouponId;
        private LocalDateTime usedAt;
        private CouponState couponState;
//        private MemberDTO.Read member;
    }
}