package studyon.app.infra.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import studyon.app.common.enums.Cache;


/**
 * Cache 관련 유틸 메소드 제공
 * @version 1.0
 * @author kcw97
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheUtils {

    public static String createIdKey(Cache cache, Object id) {
        return "%s:%s".formatted(cache.getBaseKey(), id);
    }

    public static String createBackupKey(String key) {
        return "BACKUP:%s".formatted(key);
    }

    public static String createCommonLoginValue(Long memberId) {
        return "login:%s".formatted(memberId);
    }
}
