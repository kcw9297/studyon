package studyon.app.infra.cache.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import studyon.app.infra.cache.CacheUtils;
import studyon.app.common.enums.Cache;
import studyon.app.common.utils.StrUtils;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component("customRedisCacheManager")
@RequiredArgsConstructor
public class RedisCacheManager implements CacheManager {

    private static final int MAX_RECENT_SEARCH_KEYWORD = 10; // 최대 최근 검색어 개수
    private static final Duration EXPIRATION_CACHE = Duration.ofSeconds(30); // 캐시 만료시간

    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public void recordLogin(Long memberId, String sessionId) {

        // [1] key
        String key = CacheUtils.createIdKey(Cache.MEMBER_LOGIN, memberId);

        // [2] Redis Set 자료형으로 저장
        stringRedisTemplate.opsForSet().add(key, sessionId);

        // [3] 공용 캐시에도, 로그인 회원 정보 기록
        String value = CacheUtils.createCommonLoginValue(memberId);
        stringRedisTemplate.opsForSet().add(Cache.CURRENT_LOGIN.getBaseKey(), value);
    }


    @Override
    public void removeLogout(Long memberId) {
        stringRedisTemplate.opsForSet().remove(Cache.CURRENT_LOGIN.getBaseKey(), memberId);
    }


    @Override
    public void saveProfile(Long memberId, Object profile) {
        setJsonValue(Cache.MEMBER_PROFILE, memberId, profile);
    }


    @Override
    public void removeProfile(Long memberId) {
        deleteValue(Cache.MEMBER_PROFILE, memberId);
    }


    @Override
    public void recordLatestSearch(Long memberId, String keyword) {

        // [1] Key
        String key = CacheUtils.createIdKey(Cache.MEMBER_LATEST_SEARCH, memberId);

        // [2] 중복 키워드 존재 시 제거 후 검색어 기록
        stringRedisTemplate.opsForList().remove(key, 0, keyword);
        stringRedisTemplate.opsForList().leftPush(key, keyword); // 리스트 맨 처음에 삽입

        // [3] 최대 저장 검색어 개수를 초과하는 경우, 맨 마지막 검색어 제거
        stringRedisTemplate.opsForList().trim(key, 0L, MAX_RECENT_SEARCH_KEYWORD-1L);
    }


    @Override
    public boolean recordVerifyMail(String sessionId, Object mailRequest) {

        // [1] Key
        String key = CacheUtils.createIdKey(Cache.VERIFICATION_MAIL, sessionId);

        // [2] 이미 중복되는 값이 있는지 확인
        // 존재 시 false 반환 (다시 시도하도록 유도)
        if (stringRedisTemplate.hasKey(key)) return false;

        // [3] 인증 정보를 직렬화 후 저장
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, StrUtils.toJson(mailRequest));
        return Objects.equals(result, true);
    }


    @Override
    public List<String> getLatestSearchList(Long memberId) {

        // [1] key
        String key = CacheUtils.createIdKey(Cache.MEMBER_LATEST_SEARCH, memberId);

        // [2] 검색 리스트 반환 (0번째 인덱스 부터, 최대 수용 개수까지)
        return stringRedisTemplate.opsForList().range(key, 0L, -1L);
    }

    @Override
    public <T> T getProfile(Long memberId, Class<T> type) {
        return getValue(Cache.MEMBER_PROFILE, memberId, type);
    }


    @Override
    public <T> T getMailRequest(String sessionId, Class<T> type) {
        return getValue(Cache.VERIFICATION_MAIL, sessionId, type);
    }

    // Value 자료형으로 저장 시 공통적으로 처리하는 key 생성 & 저장 로직
    private void setJsonValue(Cache cache, Object id, Object data) {

        // [1] Key
        String key = CacheUtils.createIdKey(cache, id);

        // [2] Redis Value 자료형으로 저장
        stringRedisTemplate.opsForValue().set(key, StrUtils.toJson(data));
    }

    // Value 자료형 제거 시  공통적으로 처리하는 key 생성 & 삭제 로직
    private void deleteValue(Cache cache, Object id) {

        // [1] Key
        String key = CacheUtils.createIdKey(cache, id);

        // [2] Redis Value 제거
        stringRedisTemplate.delete(key);
    }

    // Value 자료형 조회 (타입 정보 제공)
    private <T> T getValue(Cache cache, Object id, Class<T> type) {

        // [1] Key
        String key = CacheUtils.createIdKey(cache, id);

        // [2] 데이터 조회 및 역직렬화 후 반환
        String jsonCache = stringRedisTemplate.opsForValue().get(key);
        return Objects.isNull(jsonCache) ?
                null : StrUtils.fromJson(jsonCache, type);
    }


    @Override
    public <T> T getCache(String entityName, Object id, Class<T> clazz) {

        // [1] key
        String key = CacheUtils.createCacheKey(entityName, id);

        log.warn("[getCache] key - {}", key);

        // [2] 캐시 조회 후 반환 (조회 성공 시, 만료시간 갱신)
        String value = stringRedisTemplate.opsForValue().getAndExpire(key, EXPIRATION_CACHE);
        return Objects.isNull(value) ? null : StrUtils.fromJson(value, clazz);
    }


    @Override
    public <T> T getOrRecordCache(String entityName, Object id, Class<T> clazz) {

        // [1] key
        String key = CacheUtils.createCacheKey(entityName, id);
        String backupKey = CacheUtils.createBackupKey(key);

        // [2] 캐시가 이미 존재하는지 확인
        // 만약 존재하는 경우 데이터를 그대로 반환
        String value = stringRedisTemplate.opsForValue().getAndExpire(key, EXPIRATION_CACHE);
        if (Objects.nonNull(value)) return StrUtils.fromJson(value, clazz);

        // [3] 새롭게 생성해야 하는 경우
        // 이전의 같은 엔티티의 다른 캐시데이터는 모두 삭제
        createCache(key, backupKey, entityName, id);
        return null; // 캐시 데이터는 없으므로 null 반환
    }


    // 캐시 데이터 생성
    private void createCache(String key, String backupKey, String entityName, Object id) {

        // [1] 다른 백업키 유무 확인. 존재 시 삭제
        String prevKeyPattern = CacheUtils.createCacheKeyPattern(entityName, id);
        Set<String> keys = stringRedisTemplate.keys(prevKeyPattern);
        if (!keys.isEmpty()) stringRedisTemplate.delete(keys);

        // [2] Redis Value 자료형으로 저장 (백업데이터 함께 저장)
        stringRedisTemplate.opsForValue().set(key, "{}", EXPIRATION_CACHE);
        stringRedisTemplate.opsForValue().set(backupKey, "{}");
    }


    @Override
    public void updateCache(String entityName, Object id, Object cacheData) {

        // [1] key
        String key = CacheUtils.createCacheKey(entityName, id);

        // [2] 캐시 데이터 확인 및 갱신 수행
        // 존재하지 않는 경우 갱신을 수행하지 않음 (캐시가 없으면 유효하지 않은 접근으로 판단)
        stringRedisTemplate.opsForValue().set(key, StrUtils.toJson(cacheData), EXPIRATION_CACHE);
    }


    @Override
    public void removeCache(String entityName, Object id) {

        // [1] key
        String key = CacheUtils.createCacheKey(entityName, id);
        String backupKey = CacheUtils.createBackupKey(key);

        // [2] 현재 캐시와, 백업 데이터 함께 삭제
        stringRedisTemplate.delete(key);
        stringRedisTemplate.delete(backupKey);
    }


    @Override
    public <T> List<T> getAllBackupValue(String entityName, Class<T> clazz) {

        // [1] key pattern 생성
        String keyPattern = "BACK:%s:*".formatted(entityName);

        // [2] 일괄 조회 수행
        Set<String> keys = stringRedisTemplate.keys(keyPattern); // 패턴으로 조회된 모든 key
        List<String> values = stringRedisTemplate.opsForValue().multiGet(keys);

        // [3] 일괄 역직렬화 후 반환
        return Objects.isNull(values) ? List.of() : values.stream().map(value -> StrUtils.fromJson(value, clazz)).toList();
    }

    @Override
    public void removeBackupKey(String key) {
        stringRedisTemplate.delete(CacheUtils.createBackupKey(key));
    }

}
