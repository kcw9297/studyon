package studyon.app.layer.domain.lecture_video;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.lecture_index.LectureIndex;

import java.time.LocalDateTime;

/**
 * 강의 영상 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@DynamicUpdate
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureVideo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureVideoId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer seq;

    @Column(nullable = false)
    private Integer duration;

    @Column(length = 1000)
    private String videoUrl;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "lecture_index_id", nullable = false)
    private LectureIndex lectureIndex;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_file_id")
    private File videoFile;


    @Builder
    public LectureVideo(String title, Integer seq, Integer duration, String videoUrl, LectureIndex lectureIndex) {
        this.title = title;
        this.seq = seq;
        this.duration = duration;
        this.videoUrl = videoUrl;
        this.lectureIndex = lectureIndex;
    }

    public void updateVideoFile(File newFile) {
        this.videoFile = newFile;
    }
    public void updateDuration(Integer duration) {
        this.duration = duration;
    }
}
