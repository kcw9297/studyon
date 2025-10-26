package studyon.app.layer.domain.teacher;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import studyon.app.common.enums.Subject;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.member.Member;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : khj00 최초 작성
 */
/**
 * 선생님 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;

    @OnDelete(action = OnDeleteAction.CASCADE) // 회원 삭제될 시 함께 삭제
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long totalStudents;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long totalReview;

    @Column(nullable = false, columnDefinition = "DECIMAL(3,2)")
    private Double averageRating;


    @Builder
    private Teacher(Member member, Subject subject, String description) {
        this.member = member;
        this.subject = subject;
        this.description = description;

        this.averageRating = 0.00;
        this.totalReview = 0L;
        this.totalStudents = 0L;
    }

    // 선생님 생성 (초기엔 아무 값도 없음)
    public static Teacher create(Subject subject, Member member) {
        return new Teacher(member, subject, "");
    }



    /* JPA 연관관계 매서드 */
    public void updateInfo(Subject subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    public void increaseReviewCount() {
        this.totalReview++;
    }

    public void increaseTotalStudents() {
        this.totalStudents++;
    }

    public void updateAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
