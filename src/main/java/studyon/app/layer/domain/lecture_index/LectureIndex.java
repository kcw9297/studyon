package studyon.app.layer.domain.lecture_index;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;

import java.time.LocalDateTime;

/**
 * 강의 인덱스 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@DynamicUpdate
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureIndex extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureIndexId;

    @Column(nullable = false)
    private String indexTitle;

    @Column(nullable = false)
    private Long indexNumber;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Builder
    public LectureIndex(Long indexNumber, String indexTitle, Lecture lecture) {
        this.indexNumber = indexNumber;
        this.indexTitle = indexTitle;
        this.lecture = lecture;
    }

    public void update(String indexTitle, Long indexNumber) {
        this.indexTitle = indexTitle;
        this.indexNumber = indexNumber;
    }
}
