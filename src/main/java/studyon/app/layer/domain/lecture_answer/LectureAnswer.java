package studyon.app.layer.domain.lecture_answer;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
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

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private LectureAnswer parent;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;




    @Builder
    public LectureAnswer(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    /*
        강의 답변 업데이트 로직
     */

    public void updateAnswer(String content) {
        this.content = content;
    }
}
