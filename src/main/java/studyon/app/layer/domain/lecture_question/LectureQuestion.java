package studyon.app.layer.domain.lecture_question;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture_answer.LectureAnswer;
import studyon.app.layer.domain.lecture_index.LectureIndex;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;

/**
 * 강의 question
 * @version 1.0
 * @author khs00
 * 20251029 LectureIndex 추가
 */

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureQuestionId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isSolved;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_answer_id")
    private LectureAnswer lectureAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_index_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private LectureIndex lectureIndex;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public LectureQuestion(String title, String content, Boolean isSolved, Lecture lecture, Member member, LectureIndex lectureIndex) {
        this.title = title;
        this.content = content;
        this.isSolved = isSolved;
        this.lecture = lecture;
        this.member = member;
        this.lectureIndex = lectureIndex;
    }

    /*
        강의 질문 수정 관련 로직
     */

    public void updateQuestion(String title, String content, Boolean isSolved) {
        this.title = title;
        this.content = content;
        this.isSolved = isSolved;
    }

    public void setLectureAnswer(LectureAnswer lectureAnswer) {
        this.lectureAnswer = lectureAnswer;
    }
}
