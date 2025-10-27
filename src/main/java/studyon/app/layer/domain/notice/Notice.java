package studyon.app.layer.domain.notice;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import studyon.app.common.enums.NoticeType;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.file.File;

import java.time.LocalDateTime;

/**
 * 공지사항 엔티티 클래스
 * @version 1.0
 * @author kcw97
 */

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_image_id")
    private File noticeImage;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeType noticeType;

    @Column(nullable = false)
    private Boolean isActivate;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime endedAt;


    @Builder
    public Notice(String title, String content, NoticeType noticeType, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.title = title;
        this.content = content;
        this.noticeType = noticeType;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.isActivate = false;
    }

    /* 객체 구현체 제공 정적 메소드 */

    // 이미지 갱신
    public void updateNoticeImage(File noticeImage) {
        this.noticeImage = noticeImage;
    }

    // 활성 상태로 갱신
    public void activate() {
        this.isActivate = true;
    }

    // 비활성 상태로 갱신
    public void inactivate() {
        this.isActivate = false;
    }

    // 이미지 제외 일괄 갱신
    public void update(String title, String content, NoticeType noticeType, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.title = title;
        this.content = content;
        this.noticeType = noticeType;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

}
