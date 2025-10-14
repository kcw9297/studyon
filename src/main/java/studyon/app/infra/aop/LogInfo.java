package studyon.app.infra.aop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import studyon.app.common.enums.Entity;

/**
 * 로그 기록에 필요한 정보를 담은 추상 클래스 (공용 필드)
 * @version 1.0
 * @author kcw97
 */
@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class LogInfo {

    private Long targetId; // 영향을 받는 엔티티 ID
    private Entity targetEntity; // 영향을 받는 엔티티 유형
}
