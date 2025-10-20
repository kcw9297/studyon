package studyon.app.layer.domain.lecture_question;

import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.Many;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;

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

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long answerCount;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long viewCount;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isSolved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Builder
    public LectureQuestion(String title, String content, Boolean isSolved, Lecture lecture) {
        this.title = title;
        this.content = content;
        this.isSolved = isSolved;
        this.lecture = lecture;
    }

    /*
        강의 질문 수정 관련 로직
     */

    public void updateQuestion(String title, String content, Boolean isSolved) {
        this.title = title;
        this.content = content;
        this.isSolved = isSolved;
    }
}
