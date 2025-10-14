package studyon.app.layer.domain.payment;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;

/**
 * 결제 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime paidAt;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double originalPrice;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double paidPrice;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double discountPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Builder
    public Payment(Double originalPrice, Double paidPrice, Double discountPrice, Member member, Lecture lecture) {
        this.originalPrice = originalPrice;
        this.paidPrice = paidPrice;
        this.discountPrice = discountPrice;
        this.member = member;
        this.lecture = lecture;
    }
    
    /*
      할인율 업데이트 추가는 나중에 고려 후 작성 예정
     */
}
