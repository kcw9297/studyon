package studyon.app.layer.domain.teacher;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.member.Member;

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

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100, nullable = false)
    private String subject;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long totalStudents;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long totalReview;

    @Column(nullable = false, columnDefinition = "DECIMAL(3,2)")
    private Double averageRating;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @Builder
    public Teacher(String subject, String description, Long teacherId, Member member) {
        this.member = member;
        this.teacherId = teacherId;
        this.subject = subject;
        this.description = description;

        this.averageRating = 0.00;
        this.totalReview = 0L;
        this.totalStudents = 0L;
    }

    /*
        갱신 로직 - 선생님 전용 페이지
    */

    public void updateInfo(String subject, String description) {
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
