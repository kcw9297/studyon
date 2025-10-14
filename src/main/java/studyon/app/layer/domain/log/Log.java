package studyon.app.layer.domain.log;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.Action;

import java.time.LocalDateTime;


@jakarta.persistence.Entity
@Getter
@ToString
@EqualsAndHashCode
@Table(name = "logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(nullable = false, updatable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    private String ipAddress;

    @Column(nullable = false, updatable = false)
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Entity entity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Action action;

    @Column(nullable = false, updatable = false, columnDefinition = "TINYINT(1)")
    private Boolean isSuccess;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime actionAt;

    @Builder
    public Log(String email, String ipAddress, Long entityId, Entity entity, Action action, Boolean isSuccess) {
        this.email = email;
        this.ipAddress = ipAddress;
        this.entityId = entityId;
        this.entity = entity;
        this.action = action;
        this.isSuccess = isSuccess;
    }
}
