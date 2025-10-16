package studyon.app.layer.domain.member_coupon;

import ch.qos.logback.classic.spi.ConfiguratorRank;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.common.enums.CouponState;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.coupon.Coupon;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;
/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * 멤버 쿠폰 엔티티
 * @version 1.0
 * @author khj00
 */
@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCouponId;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime issuedAt;

    private LocalDateTime usedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponState couponState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public MemberCoupon(Member member, Coupon coupon, CouponState couponState, LocalDateTime usedAt) {
        this.member = member;
        this.coupon = coupon;
        this.couponState = couponState;
        this.usedAt = usedAt;
    }

    // 쿠폰 사용 처리 메소드

    public void useCoupon() {
        if (this.couponState == CouponState.USED) {
            throw new IllegalArgumentException("이미 사용된 쿠폰입니다.");
        }
        this.couponState = CouponState.USED;
        this.usedAt = LocalDateTime.now();
    }
}
