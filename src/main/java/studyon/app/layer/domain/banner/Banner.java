package studyon.app.layer.domain.banner;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.file.File;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-30) : kcw97 최초 작성
 */

/**
 * 배너 엔티티 클래스
 * @version 1.0
 * @author kcw97
 */

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Banner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_image_id")
    private File bannerImage;

    @Column(nullable = false, unique = true, updatable = false, columnDefinition = "INT(1)")
    private Integer idx; // 배너 개수와 순서가 고정 (순서있는 카드 형태 제공)

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isActivate;



    @Builder
    public Banner(Integer idx) {
        this.idx = idx;
        this.title = "";
        this.isActivate = false;
    }

    /* 객체 구현체 제공 정적 메소드 */

    // 이미지 제외 일괄 갱신
    public void updateTitle(String title) {
        this.title = title;
    }

    // 이미지 갱신
    public void updateBannerImage(File noticeImage) {
        this.bannerImage = noticeImage;
    }

    // 활성 상태로 갱신
    public void activate() {
        this.isActivate = true;
    }

    // 비활성 상태로 갱신
    public void inactivate() {
        this.isActivate = false;
    }

    // 비활성 상태로 갱신
    public void initialize() {
        this.bannerImage = null;
        this.title = "";
        this.isActivate = false;
    }



}
