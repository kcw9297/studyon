package studyon.app.infra.cache.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import studyon.app.common.enums.Cache;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.RedisUtils;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class EditorCacheManagerImpl implements EditorCacheManager {

    private final StringRedisTemplate stringRedisTemplate;

    // 만료 시간
    private final Duration cacheExpire = Duration.ofMinutes(5);
    private final Duration backupExpire = Duration.ofMinutes(30);


    @Override
    public void recordEditorCache(Object id, Object cacheData) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.EDITOR, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 캐시데이터 갱신 (없을 시 새로 생성)
        stringRedisTemplate.opsForValue().set(key, StrUtils.toJson(cacheData), cacheExpire);
        stringRedisTemplate.opsForValue().set(backupKey, StrUtils.toJson(cacheData), backupExpire);
    }


    @Override
    public <T> T getEditorCache(Object id, Class<T> clazz) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.EDITOR, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 에디터 캐시데이터 조화 후 반환
        String cacheObj = stringRedisTemplate.opsForValue().getAndExpire(key, cacheExpire);
        stringRedisTemplate.opsForValue().getAndExpire(backupKey, backupExpire);
        return Objects.isNull(cacheObj) ? null : StrUtils.fromJson(cacheObj, clazz);
    }


    @Override
    public void updateEditorCache(Object id, Object cacheData) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.EDITOR, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 캐시데이터 갱신 (없을 시 새로 생성)
        stringRedisTemplate.opsForValue().set(key, StrUtils.toJson(cacheData), cacheExpire);
        stringRedisTemplate.opsForValue().set(backupKey, StrUtils.toJson(cacheData), backupExpire);
    }


    @Override
    public void setCacheWithShortExpired(Object id, Object cacheData) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.EDITOR, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 에디터 캐시데이터 조회 후 반환
        stringRedisTemplate.opsForValue().setIfPresent(key, StrUtils.toJson(cacheData), Duration.ofMillis(30));
        stringRedisTemplate.opsForValue().setIfPresent(backupKey, StrUtils.toJson(cacheData), Duration.ofMinutes(30));
    }


    @Override
    public <T> List<T> getAndRemoveAllOrphanCache(Class<T> clazz) {

        // [1] 키 패턴 생성
        String originalPattern = RedisUtils.createEditorPattern();
        String backupPattern = RedisUtils.createBackupPattern();

        // [2] 키 목록 조회
        Set<String> originalKeys = stringRedisTemplate.keys(originalPattern);
        Set<String> backupKeys = stringRedisTemplate.keys(backupPattern);
        log.warn("originalKeys: {}, backupKeys {}", originalKeys, backupKeys);

        // [3] 고아 상태의 backup key 필터 후 삭제
        List<String> orphanKeys = backupKeys.stream()
                .filter(backup -> !originalKeys.contains(backup.substring(backup.indexOf(":")+1)))
                .toList();

        // [5] 고아키 백업데이터 일괄 조회 및 변환하여 반환
        List<String> orphanCaches = stringRedisTemplate.opsForValue().multiGet(orphanKeys);
        stringRedisTemplate.delete(orphanKeys);

        // 결과가 null 인 경우, 빈 리스트 반환
        if (Objects.isNull(orphanCaches)) return List.of();

        // [6] 고아 데이터 삭제 및, 고아 키정보 반환
        return orphanCaches.stream()
                .filter(Objects::nonNull)
                .map(orphanCache -> StrUtils.fromJson(orphanCache, clazz))
                .toList();
    }


}
