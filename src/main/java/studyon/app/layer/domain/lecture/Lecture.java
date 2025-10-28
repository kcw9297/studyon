package studyon.app.layer.domain.lecture;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import studyon.app.common.enums.LectureRegisterStatus;
import studyon.app.common.enums.LectureTarget;
import studyon.app.common.enums.Subject;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.common.enums.Difficulty;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.teacher.Teacher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 강의 엔티티 클래스
 * @version 1.1
 * @author khj00
 * 20251023 Subject추가
 * 202251024 LectureRegisterStatus
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

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long price;

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

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("rating DESC")
    private List<LectureReview> lectureReviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LectureRegisterStatus lectureRegisterStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LectureTarget lectureTarget;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_file_id")
    private File thumbnail;



    @Builder
    public Lecture(String title, String description, Long price,
                   Difficulty difficulty, Teacher teacher, Subject subject,
                   LectureTarget lectureTarget, LectureRegisterStatus lectureRegisterStatus) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.publishDate = LocalDateTime.now();
        this.lectureTarget = (lectureTarget != null) ? lectureTarget : LectureTarget.HIGH1;

        this.lectureRegisterStatus = lectureRegisterStatus;
        this.difficulty = difficulty;
        this.videoCount = 0L;
        this.totalDuration = 0L;
        this.totalStudents = 0L;
        this.averageRate = 0.00;
        this.likeCount = 0L;
        this.onSale = false;
        this.subject = subject;

        this.teacher = teacher;
        this.lectureRegisterStatus = (lectureRegisterStatus != null)
                ? lectureRegisterStatus
                : LectureRegisterStatus.UNREGISTERED;
    }

    public void updateThumbnail(File newThumbnail) {
        this.thumbnail = newThumbnail;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "lectureId=" + lectureId +
                ", title='" + title + '\'' +
                ", registerStatus=" + lectureRegisterStatus +
                ", difficulty=" + difficulty +
                ", onSale=" + onSale +
                '}';
    }

    /*
        JPA 변경 감지를 이용한 갱신 로직 (setter) - 강의 수정 페이지 연동 갱신
     */

    public void update(String title, String description, Long price,
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
