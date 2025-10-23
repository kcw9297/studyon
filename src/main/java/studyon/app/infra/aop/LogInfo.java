package studyon.app.infra.aop;

import lombok.*;
import lombok.experimental.SuperBuilder;
import studyon.app.common.enums.Entity;

/**
 * 로그 기록에 필요한 정보를 담은 추상 클래스 (공용 필드)
 * @version 1.0
 * @author kcw97
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class LogInfo {

    private Long targetId; // 영향을 받는 엔티티 ID
    private Entity targetEntity; // 영향을 받는 엔티티 유형

    /**
     * 로깅을 위한 타겟 정보 셋팅 (엔티티가 생성되는 경우)
     * @param targetId 대상 PK
     * @param targetEntity 대상 Entity
     */
    public void setTarget(Long targetId,  Entity targetEntity) {
        this.targetId = targetId;
        this.targetEntity = targetEntity;
    }
}
