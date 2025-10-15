package studyon.app.layer.domain.lecture_question;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureQnaId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long answerCount;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long viewCount;

    @Column(nullable = false)
    private boolean isSolved;

    @Builder
    public LectureQuestion(String title, String content, boolean isSolved) {
        this.title = title;
        this.content = content;
        this.isSolved = isSolved;
    }
}
