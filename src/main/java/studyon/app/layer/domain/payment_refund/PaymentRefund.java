package studyon.app.layer.domain.payment_refund;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.payment.Payment;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-22) : kcw97 PaymentDetails 연관관계 제거
 */

/**
 * 환불 정보 엔티티 클래스
 * @version 1.1
 * @author khj00
 */

@Entity
@Getter
@DynamicUpdate
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRefund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderRefundId;

    @Column(nullable = false)
    private String refundReason;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double refundPrice;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isRefunded;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;


    @Builder
    public PaymentRefund(String refundReason, Double refundPrice, boolean isRefunded, Payment payment) {
        this.refundReason = refundReason;
        this.refundPrice = refundPrice;
        this.isRefunded = isRefunded;
        this.payment = payment;
    }

    /*
        환불 정보 업데이트 로직들(나중에 추가)
     */
}
