package studyon.app.layer.domain.lecture;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.common.enums.Difficulty;

/**
 * ENTITY (= Table) 유형
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

     */
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long videoCount;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long totalDuration;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long totalStudents;

    @Column(nullable = false, columnDefinition = "DECIMAL(3,2)")
    private Double averageRate;

    @Column(nullable = false)
    private Long likeCount;

    @Builder
    public Lecture(String title, String description, Double price,
                   Difficulty difficulty) {
        //his.teacher = teacher;
        this.title = title;
        this.description = description;
        this.price = price;
        this.difficulty = difficulty;
        this.videoCount = 0L;
        this.totalDuration = 0L;
        this.totalStudents = 0L;
        this.averageRate = 0.00;
        this.likeCount = 0L;
    }
}
