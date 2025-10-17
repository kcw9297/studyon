package studyon.app.layer.domain.lecture_review;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : khj00 최초 작성
 */

/**
 * 강의 리뷰 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureReviewId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Min(1)
    @Max(5)
    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer rating;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @Builder
    public LectureReview(String content, Integer rating, Lecture lecture, Member member) {
        this.content = content;
        this.rating = rating;
        this.lecture = lecture;
        this.member = member;
    }

    /*
       강의 리뷰 업데이트 로직
     */

    public void updateReviewContent(String content, Integer rating) {
        this.content = content;
        this.rating = rating;
    }
}
