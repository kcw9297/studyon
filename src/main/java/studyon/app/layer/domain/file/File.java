package studyon.app.layer.domain.file;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.common.enums.Entity;


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


    @Builder
    public File(String originalName, String storeName, String ext, Long size, Long entityId, Entity entity) {
        this.originalName = originalName;
        this.storeName = storeName;
        this.ext = ext;
        this.size = size;
        this.entityId = entityId;
        this.entity = entity;
    }
}
