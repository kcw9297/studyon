package studyon.app.layer.domain.payment_details;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.payment.Payment;

/**
 * 결제 정보 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentDetails extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PaymentDetailId;

    @Column(columnDefinition = "TEXT")
    private String PaymentApiResult;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Builder
    public PaymentDetails(String paymentApiResult, Payment payment) {
        this.PaymentApiResult = paymentApiResult;
        this.payment = payment;
    }
}
