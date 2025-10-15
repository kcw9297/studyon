package studyon.app.layer.domain.payment_refund;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment_details.PaymentDetails;

import java.time.LocalDateTime;

/**
 * 환불 정보 엔티티 클래스
 * @version 1.0
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

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_detail_id", nullable = false)
    private PaymentDetails paymentDetails;



    @Builder
    public PaymentRefund(String refundReason, Double refundPrice, boolean isRefunded, Payment payment, PaymentDetails paymentDetails) {
        this.refundReason = refundReason;
        this.refundPrice = refundPrice;
        this.isRefunded = isRefunded;
        this.payment = payment;
        this.paymentDetails = paymentDetails;
    }

    /*
        환불 정보 업데이트 로직들(나중에 추가)
     */
}
