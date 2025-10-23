package studyon.app.layer.domain.payment;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-22) : kcw97 PaymentDetails 통합
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

    @Column(nullable = false, updatable = false)
    private String paymentUid; // 결제 대행사에서 제공한 결제 고유번호

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double paidAmount;

    @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
    private String paymentApiResult;

    @Column(nullable = false)
    private Boolean isRefunded;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime refundedAt;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;


    @Builder
    public Payment(String paymentUid, Double paidAmount, Member member, Lecture lecture, String paymentApiResult) {
        this.paymentUid = paymentUid;
        this.paidAmount = paidAmount;
        this.paymentApiResult = paymentApiResult;
        this.member = member;
        this.lecture = lecture;
        this.isRefunded = false;
        this.refundedAt = null;
    }

}
