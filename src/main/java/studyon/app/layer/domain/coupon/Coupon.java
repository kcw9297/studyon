package studyon.app.layer.domain.coupon;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * 쿠폰 엔티티 클래스
 * @version 1.0
 * @author khj00
 */


@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double discountPrice;

    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double discountRate;

    @Column(nullable = false)
    private LocalDateTime validFrom;

    @Column(nullable = false)
    private LocalDateTime validUntil;

    @Column(length = 100)
    private String availableScope;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double discountLimit;

    @Builder
    public Coupon(String name, Double discountPrice, Double discountRate, LocalDateTime validFrom, LocalDateTime validUntil, String availableScope, Double discountLimit) {
        this.name = name;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.availableScope = availableScope;
        this.discountLimit = discountLimit;
    }
}
