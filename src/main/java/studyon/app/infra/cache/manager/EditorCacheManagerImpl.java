package studyon.app.infra.cache.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import studyon.app.common.enums.Cache;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.RedisUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class EditorCacheManagerImpl implements EditorCacheManager {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void updateEditorCache(Object id, Object cacheData) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.EDITOR, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 캐시데이터 갱신 (없을 시 새로 생성)
        stringRedisTemplate.opsForValue().set(key, StrUtils.toJson(cacheData), Duration.ofSeconds(30));
        stringRedisTemplate.opsForValue().set(backupKey, StrUtils.toJson(cacheData), Duration.ofMinutes(1));
    }

    @Override
    public <T> T getEditorCache(Object id, Class<T> clazz) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.EDITOR, id);

        // [2] 에디터 캐시데이터 조화 후 반환
        String cacheObj = stringRedisTemplate.opsForValue().getAndExpire(key, Duration.ofMinutes(30));
        return Objects.isNull(cacheObj) ? null : StrUtils.fromJson(cacheObj, clazz);
    }

    @Override
    public <T> T getAndRemoveCache(Object id, Class<T> clazz) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.EDITOR, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 에디터 캐시데이터 조화 후 반환
        String cacheObj = stringRedisTemplate.opsForValue().getAndDelete(key);
        stringRedisTemplate.delete(backupKey);
        return Objects.isNull(cacheObj) ? null : StrUtils.fromJson(cacheObj, clazz);
    }


    @Override
    public <T> List<T> getAndRemoveAllOrphanCache(Object id, Class<T> clazz) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.EDITOR, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 키 패턴 생성
        String originalPattern = RedisUtils.createEditorPattern();
        String backupPattern = RedisUtils.createBackupPattern();

        // [3] 키 목록 조회
        Set<String> originalKeys = stringRedisTemplate.keys(originalPattern);
        Set<String> backupKeys = stringRedisTemplate.keys(backupPattern);

        // [4] 고아 상태의 backup key 필터
        List<String> orphanKeys = backupKeys.stream()
                .map(backup -> backup.substring(backup.indexOf(":"))+1)
                .filter(originalKeys::contains)
                .toList();

        // [5] 고아키 백업데이터 일괄 조회 및 변환하여 반환
        List<String> orphanCaches = stringRedisTemplate.opsForValue().multiGet(orphanKeys);

        return Objects.isNull(orphanCaches) ?
                List.of() : orphanCaches.stream().map(originalKey -> StrUtils.fromJson(originalKey, clazz)).toList();
    }


}
