package studyon.app.infra.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import studyon.app.common.constant.Param;
import studyon.app.common.enums.Cache;


/**
 * Cache 관련 유틸 메소드 제공
 * @version 1.0
 * @author kcw97
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RedisUtils {

    public static String createIdKey(Cache cache, Object id) {
        return "%s:%s".formatted(cache.getBaseKey(), id);
    }

    public static String createIdKey(Cache cache, Object id1, Object id2) {
        return "%s:%s:%s".formatted(cache.getBaseKey(), id1, id2);
    }

    public static String createBackupKey(String key) {
        return "%s:%s".formatted(Param.KEY_BACKUP, key);
    }

    public static String createCommonLoginValue(Long memberId) {
        return "%s:%s".formatted(Param.KEY_LOGIN, memberId);
    }

    public static String createCacheKey(String entityName, String methodType, Object id) {
        return "%s:%s:%s:%s".formatted(Param.KEY_CACHE, entityName, methodType, id);
    }

    public static String createCacheKey(String entityName, String methodType, Long entityId, Object id) {
        return "%s:%s:%s:%s:%s".formatted(Param.KEY_CACHE, entityName, methodType, entityId, id);
    }

    public static String createEditorPattern() {
        return "%s:*".formatted(Cache.EDITOR);
    }

    public static String createBackupPattern() {
        return "%s:%s:*".formatted(Param.KEY_BACKUP, Cache.EDITOR);
    }
}
