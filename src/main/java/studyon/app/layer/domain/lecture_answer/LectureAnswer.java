package studyon.app.layer.domain.lecture_answer;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture_question.LectureQuestion;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;

/**
 * 강의 질문에 대한 답변 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@DynamicUpdate
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureAnswer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureAnswerId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private LectureAnswer parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_question_id", nullable = false)
    private LectureQuestion lectureQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @Builder
    public LectureAnswer(String content, LectureQuestion lectureQuestion, Member member) {
        this.content = content;
        this.lectureQuestion = lectureQuestion;
        this.member = member;
    }

    /*
        강의 답변 업데이트 로직
     */

    public void updateAnswer(String content) {
        this.content = content;
    }
}
