package studyon.app.layer.domain.file;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.common.enums.FileType;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.common.enums.Entity;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-22) : kcw97 FileType 추가
 */

/**
 * 파일 엔티티 클래스
 * @version 1.1
 * @author kcw97
 */

@jakarta.persistence.Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@Table(name = "files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false, unique = true)
    private String storeName;

    @Column(nullable = false)
    private String ext;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, updatable = false)
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Entity entity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private FileType fileType;

    @Column(nullable = false)
    private String filePath;

    @Builder
    public File(String originalName, String storeName, String ext, Long size, Long entityId, Entity entity, FileType fileType, String filePath) {
        this.originalName = originalName;
        this.storeName = storeName;
        this.ext = ext;
        this.size = size;
        this.entityId = entityId;
        this.entity = entity;
        this.fileType = fileType;
        this.filePath = filePath;
    }

    /* JPA 연관관계 메소드 */

    public void update(String originalName, String ext, Long size) {
        this.originalName = originalName;
        this.ext = ext;
        this.size = size;
    }
}
