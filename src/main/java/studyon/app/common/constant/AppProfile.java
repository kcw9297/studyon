package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Spring Boot Application Profile 상수 관리
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppProfile {

    public static final String LOCAL = "local";    // 로컬 개발용
    public static final String PROD = "prod";      // 실제 배포용
}
