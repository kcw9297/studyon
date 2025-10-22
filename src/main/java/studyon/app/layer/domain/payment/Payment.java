package studyon.app.layer.domain.payment;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-22) : kcw97 PaymentDetails 정보 통합
 */
/**
 * 결제 엔티티 클래스
 * @version 1.1
 * @author khj00
 */

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double paidPrice;

    @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
    private String paymentApiResult;

    @Column(nullable = false)
    private Boolean isRefunded;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime refundedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;


    @Builder
    public Payment(Double paidPrice, Member member, Lecture lecture, String paymentApiResult) {
        this.paidPrice = paidPrice;
        this.paymentApiResult = paymentApiResult;
        this.member = member;
        this.lecture = lecture;
        this.isRefunded = false;
        this.refundedAt = null;
    }

}
