package studyon.app.layer.domain.lecture;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import studyon.app.common.enums.*;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.teacher.Teacher;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-23) : khj00 Subject추가
 *  ▶ ver 1.2 (2025-10-24) : khj00 LectureRegisterStatus 추가
 *  ▶ ver 1.3 (2025-10-28) : phj03 좋아요 카운트 기능 추가
 *  ▶ ver 1.4 (2025-10-28) : kcw97 @OneToMany 제거 및 엔티티 변수 위치 조정
 *  ▶ ver 1.5 (2025-10-29) : Subject detail 추가
 */

/**
 * 강의 엔티티 클래스
 * @version 1.4
 * @author khj00
 */

@Entity
@Getter
@DynamicUpdate
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_file_id")
    private File thumbnailFile;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectDetail subjectDetail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LectureRegisterStatus lectureRegisterStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LectureTarget lectureTarget;


    @Builder
    public Lecture(String title, String description, Long price,
                   Difficulty difficulty, Teacher teacher, Subject subject,
                   LectureTarget lectureTarget,SubjectDetail subjectDetail) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.publishDate = LocalDateTime.now();
        this.lectureTarget = lectureTarget;
        this.lectureRegisterStatus = LectureRegisterStatus.UNREGISTERED;
        this.difficulty = difficulty;
        this.subject = subject;
        this.subjectDetail = subjectDetail;
        this.teacher = teacher;

        this.videoCount = 0L;
        this.totalDuration = 0L;
        this.totalStudents = 0L;
        this.averageRate = 0.00;
        this.likeCount = 0L;
        this.onSale = true;
    }


    /* JPA 변경 감지를 이용한 갱신 로직 (setter) - 강의 수정 페이지 연동 갱신 */

    public void update(String title, String description, Long price,
                       Boolean onSale, Difficulty difficulty) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.onSale = onSale;
        this.difficulty = difficulty;
    }

    public void updateThumbnail(File thumbnailFile) {
        this.thumbnailFile = thumbnailFile;
    }

    public void updateAverageRate(Double avg) {
        this.averageRate = avg;
    }

    public void updateLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
