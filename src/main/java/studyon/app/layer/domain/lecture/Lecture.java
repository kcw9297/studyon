package studyon.app.layer.domain.lecture;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.common.enums.Subject;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.common.enums.Difficulty;
import studyon.app.layer.domain.teacher.Teacher;

import java.time.LocalDateTime;

/**
 * 강의 엔티티 클래스
 * @version 1.1
 * @author khj00
 * 20251023 Subject추가
 */

@Entity
@Getter
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(nullable = false)
    private Long videoCount;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long totalDuration;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long totalStudents;

    @Column(nullable = false, columnDefinition = "DECIMAL(3,2)")
    private Double averageRate;

    @Column(nullable = false)
    private Long likeCount;

    @Column(nullable = false)
    private Boolean onSale;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime publishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Subject subject;

    @Builder
    public Lecture(String title, String description, Double price,
                   Difficulty difficulty, Teacher teacher) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.publishDate = LocalDateTime.now();

        this.difficulty = difficulty;
        this.videoCount = 0L;
        this.totalDuration = 0L;
        this.totalStudents = 0L;
        this.averageRate = 0.00;
        this.likeCount = 0L;
        this.onSale = false;

        this.teacher = teacher;
    }

    /*
        JPA 변경 감지를 이용한 갱신 로직 (setter) - 강의 수정 페이지 연동 갱신
     */

    public void update(String title, String description, Double price,
                       Boolean onSale, Difficulty difficulty) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.onSale = onSale;
        this.difficulty = difficulty;
    }

    // 강의 평점 업데이트 Setter

    public void updateAverageRate(Double avg) {
        this.averageRate = avg;
    }
}
