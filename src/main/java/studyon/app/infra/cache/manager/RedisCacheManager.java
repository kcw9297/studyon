package studyon.app.infra.cache.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.Param;
import studyon.app.infra.cache.RedisUtils;
import studyon.app.common.enums.Cache;
import studyon.app.common.utils.StrUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component("customRedisCacheManager")
@RequiredArgsConstructor
public class RedisCacheManager implements CacheManager {

    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public void recordLogin(Long memberId, String sessionId) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.MEMBER_LOGIN, memberId);

        // [2] Redis Set 자료형으로 저장
        stringRedisTemplate.opsForSet().add(key, sessionId);

        // [3] 공용 캐시에도, 로그인 회원 정보 기록
        String value = RedisUtils.createCommonLoginValue(memberId);
        stringRedisTemplate.opsForSet().add(Cache.CURRENT_LOGIN.getBaseKey(), value);
    }


    @Override
    public void removeLogout(Long memberId) {
        String value = RedisUtils.createCommonLoginValue(memberId);
        stringRedisTemplate.opsForSet().remove(Cache.CURRENT_LOGIN.getBaseKey(), value);
    }


    @Override
    public void recordLatestSearch(Long memberId, String keyword) {

        // [1] Key
        String key = RedisUtils.createIdKey(Cache.MEMBER_LATEST_SEARCH, memberId);

        // [2] 중복 키워드 존재 시 제거 후 검색어 기록
        stringRedisTemplate.opsForList().remove(key, 0, keyword);
        stringRedisTemplate.opsForList().leftPush(key, keyword); // 리스트 맨 처음에 삽입

        // [3] 최대 저장 검색어 개수를 초과하는 경우, 맨 마지막 검색어 제거
        stringRedisTemplate.opsForList().trim(key, 0, 9); // 최대 10개
    }


    @Override
    public List<String> getLatestSearchList(Long memberId) {

        // [1] key
        String key = RedisUtils.createIdKey(Cache.MEMBER_LATEST_SEARCH, memberId);

        // [2] 검색 리스트 반환 (0번째 인덱스 부터, 최대 수용 개수까지)
        return stringRedisTemplate.opsForList().range(key, 0L, -1L);
    }

    @Override
    public boolean recordAuthRequest(String target, String token, Duration timeout) {

        // [1] Key
        String targetKey = RedisUtils.createIdKey(Cache.AUTH, target); // 중복 요청을 막기 위한 key
        String tokenKey = RedisUtils.createIdKey(Cache.AUTH, token); // 실제로 인증 시 사용하는 key

        // [2] 최근 인증 요청이 있는지 확인
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(targetKey, "", Duration.ofMinutes(1));
        if (Objects.isNull(result) || !result) return false; // 만약 실패 시, 이미 요청이 존재하는 경우이거나 오류 발생

        // [3] 정상 처리된 경우, 실제 인증을 위한 key 생성 후 true 반환
        stringRedisTemplate.opsForValue().set(tokenKey, "", Duration.ofMinutes(5)); // 5분 유효
        return true;
    }


    @Override
    public boolean recordAuthRequest(String target, String token, Duration timeout, Object authRequest) {

        // [1] Key & pattern
        String targetKey = RedisUtils.createIdKey(Cache.AUTH, target); // 중복 요청을 막기 위한 key
        String tokenKey = RedisUtils.createIdKey(Cache.AUTH, token); // 실제로 인증 시 사용하는 key


        // [2] 최근 인증 요청이 있는지 확인
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(targetKey, StrUtils.toJson(authRequest), Duration.ofMinutes(3));
        if (Objects.isNull(result) || !result) return false; // 만약 실패 시, 이미 요청이 존재하는 경우이거나 오류 발생

        // [3] 정상 처리된 경우, 실제 인증을 위한 key 생성 후 true 반환
        stringRedisTemplate.opsForValue().set(tokenKey, StrUtils.toJson(authRequest), Duration.ofMinutes(3)); // 5분 유효
        return true;
    }


    @Override
    public boolean isAuthRequestValid(String token) {

        // [1] Key
        String key = RedisUtils.createIdKey(Cache.AUTH, token);

        // [2] 데이터 조회 및 역직렬화 후 반환
        return stringRedisTemplate.hasKey(key);
    }


    @Override
    public <T> T getAuthRequest(String token, Class<T> type) {

        // [1] Key
        String key = RedisUtils.createIdKey(Cache.AUTH, token);

        // [2] 데이터 조회 및 역직렬화 후 반환
        String jsonCache = stringRedisTemplate.opsForValue().get(key);
        return Objects.isNull(jsonCache) ? null : StrUtils.fromJson(jsonCache, type);
    }


    @Override
    public void removeAuthRequest(String token) {
        stringRedisTemplate.delete(RedisUtils.createIdKey(Cache.AUTH, token));
    }


    @Override
    public void recordPaymentRequest(Long memberId, Long lectureId, Object paymentRequest) {

        // [1] Key
        String key = RedisUtils.createIdKey(Cache.PAYMENT, memberId, lectureId);

        // [2] 저장 수행
        stringRedisTemplate.opsForValue().set(key, StrUtils.toJson(paymentRequest), Duration.ofMinutes(3));
    }


    @Override
    public <T> T getPaymentRequest(Long memberId, Long lectureId, Class<T> dataType) {

        // [1] Key
        String key = RedisUtils.createIdKey(Cache.PAYMENT, memberId, lectureId);

        // [2] 요청 조회 후, 역직렬화 하여 반환 (조회 시 TTL 연장)
        String jsonCache = stringRedisTemplate.opsForValue().getAndExpire(key, Duration.ofMinutes(3));
        return Objects.isNull(jsonCache) ? null : StrUtils.fromJson(jsonCache, dataType);
    }


    @Override
    public <T> T getAndDeletePaymentRequest(Long memberId, Long lectureId, Class<T> dataType) {

        // [1] Key
        String key = RedisUtils.createIdKey(Cache.PAYMENT, memberId, lectureId);

        // [2] 요청 조회 및 삭제 후, 역직렬화 하여 반환
        String jsonCache = stringRedisTemplate.opsForValue().getAndDelete(key);
        return Objects.isNull(jsonCache) ? null : StrUtils.fromJson(jsonCache, dataType);
    }


    // Value 자료형으로 저장 시 공통적으로 처리하는 key 생성 & 저장 로직
    private void setJsonValue(Cache cache, Object id, Object data) {

        // [1] Key
        String key = RedisUtils.createIdKey(cache, id);

        // [2] Redis Value 자료형으로 저장
        stringRedisTemplate.opsForValue().set(key, StrUtils.toJson(data));
    }

    // Value 자료형 제거 시  공통적으로 처리하는 key 생성 & 삭제 로직
    private void deleteValue(Cache cache, Object id) {

        // [1] Key
        String key = RedisUtils.createIdKey(cache, id);

        // [2] Redis Value 제거
        stringRedisTemplate.delete(key);
    }

    // Value 자료형 조회 (타입 정보 제공)
    private <T> T getValue(Cache cache, Object id, Class<T> type) {

        // [1] Key
        String key = RedisUtils.createIdKey(cache, id);

        // [2] 데이터 조회 및 역직렬화 후 반환
        String jsonCache = stringRedisTemplate.opsForValue().get(key);
        return Objects.isNull(jsonCache) ?
                null : StrUtils.fromJson(jsonCache, type);
    }


    @Override
    public <T> T getCache(String entityName, String methodType, Object id, Class<T> clazz) {

        // [1] key
        String key = RedisUtils.createCacheKey(entityName, methodType, id);
        log.warn("key = {}", key);

        // [2] 캐시 조회 후 반환 (조회 성공 시, 만료시간 갱신)
        return getCacheValue(clazz, key);
    }


    @Override
    public <T> T getCache(String entityName, String methodType, Long entityId, Object id, Class<T> clazz) {

        // [1] key
        String key = RedisUtils.createCacheKey(entityName, methodType, entityId, id);
        log.warn("key = {}", key);

        // [2] 캐시 조회 후 반환 (조회 성공 시, 만료시간 갱신)
        return getCacheValue(clazz, key);
    }

    private <T> T getCacheValue(Class<T> clazz, String key) {
        String value = stringRedisTemplate.opsForValue().getAndExpire(key, Param.EXPIRATION_CACHE);
        return Objects.isNull(value) ? null : StrUtils.fromJson(value, clazz);
    }


    @Override
    public <T> T getCacheAndDeleteOldCache(String entityName, String methodType, Long entityId, Object id, Class<T> clazz) {

        // [1] key & key pattern (CACHE:LECTURE_QUESTION:EDIT:*:sessionId)
        String findKey = RedisUtils.createCacheKey(entityName, methodType, entityId, id);
        String pattern = RedisUtils.createAnyEntityIdPattern(entityName, methodType, entityId);
        log.warn("key = {}, pattern = {}", findKey, pattern);

        // [2] 기존 키 조회
        Set<String> keys = stringRedisTemplate.keys(pattern);
        if (keys.isEmpty()) return null; // 아무것도 존재하지 않으면 return

        // [3] 현재 캐시를 제외한 모든 캐시 삭제
        // 매칭되는 키, 매칭되지 않는 키를 나눔
        Map<Boolean, List<String>> partitioned = keys.stream()
                .collect(Collectors.partitioningBy(key -> Objects.equals(key, findKey)));

        // 매칭되는 키는 반환, 매칭되지 않는(과거 키)는 삭제
        List<String> matched = partitioned.get(true);
        List<String> unmatched = partitioned.get(false);

        unmatched.forEach(stringRedisTemplate::delete);
        return matched.isEmpty() ? null : StrUtils.fromJson(matched.get(0), clazz);
    }



    @Override
    public <T> T getOrRecordCache(String entityName, String methodType, Object id, Class<T> clazz) {

        // [1] key
        String key = RedisUtils.createCacheKey(entityName, methodType, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 캐시가 이미 존재하는지 확인
        // 만약 존재하는 경우 데이터를 그대로 반환
        String value = stringRedisTemplate.opsForValue().getAndExpire(key, Param.EXPIRATION_CACHE);
        if (Objects.nonNull(value)) return StrUtils.fromJson(value, clazz);

        // [3] 새롭게 생성해야 하는 경우
        // 이전의 같은 엔티티의 다른 캐시데이터는 모두 삭제
        stringRedisTemplate.opsForValue().set(key, "{}", Param.EXPIRATION_CACHE);
        stringRedisTemplate.opsForValue().set(backupKey, "{}");
        return null; // 캐시 데이터는 없으므로 null 반환
    }


    @Override
    public void updateCache(String entityName, String actionType, Object id, Object cacheData) {

        // [1] key
        String key = RedisUtils.createCacheKey(entityName, actionType, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 캐시 데이터 확인 및 갱신 수행
        // 존재하지 않는 경우 갱신을 수행하지 않음 (캐시가 없으면 유효하지 않은 접근으로 판단)
        stringRedisTemplate.opsForValue().set(key, StrUtils.toJson(cacheData), Param.EXPIRATION_CACHE);
        stringRedisTemplate.opsForValue().set(backupKey, StrUtils.toJson(cacheData));
    }

    @Override
    public void updateCache(String entityName, String actionType, Long entityId, Object id, Object cacheData) {

        // [1] key
        String key = RedisUtils.createCacheKey(entityName, actionType, entityId, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 캐시 데이터 확인 및 갱신 수행
        // 존재하지 않는 경우 갱신을 수행하지 않음 (캐시가 없으면 유효하지 않은 접근으로 판단)
        stringRedisTemplate.opsForValue().set(key, StrUtils.toJson(cacheData), Param.EXPIRATION_CACHE);
        stringRedisTemplate.opsForValue().set(backupKey, StrUtils.toJson(cacheData));
    }


    @Override
    public void removeCache(String entityName, String actionType, Object id) {
        stringRedisTemplate.delete(RedisUtils.createCacheKey(entityName, actionType, id));
    }


    @Override
    public void removeCache(String entityName, String actionType, Long entityId, Object id) {
        stringRedisTemplate.delete(RedisUtils.createCacheKey(entityName, actionType, entityId, id));
    }


    @Override
    public void removeCacheAndBackup(String entityName, String actionType, Object id) {

        // [1] key
        String key = RedisUtils.createCacheKey(entityName, actionType, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 현재 캐시와, 백업 데이터 함께 삭제
        stringRedisTemplate.delete(key);
        stringRedisTemplate.delete(backupKey);
    }

    @Override
    public void removeCacheAndBackup(String entityName, String actionType, Long entityId, Object id) {

        // [1] key
        String key = RedisUtils.createCacheKey(entityName, actionType, id);
        String backupKey = RedisUtils.createBackupKey(key);

        // [2] 현재 캐시와, 백업 데이터 함께 삭제
        stringRedisTemplate.delete(key);
        stringRedisTemplate.delete(backupKey);
    }


    @Override
    public <T> List<T> getAndRemoveAllBackup(String entityName, Class<T> clazz) {

        // [1] key pattern 생성
        // ex. BACKUP:CACHE:LECTURE_QUESTION:*
        String keyPattern = "%s:%s:%s:*".formatted(Param.KEY_BACKUP, Param.KEY_CACHE, entityName);

        // [2] 백업 key 전체 조회
        Set<String> backupKeys = stringRedisTemplate.keys(keyPattern);
        if (backupKeys.isEmpty()) return List.of();

        // [3] 고아 상태만 필터링 후 반환
        return backupKeys.stream()
                .filter(backupKey -> {
                    String originalKey = backupKey.replaceFirst("^%s:".formatted(Param.KEY_BACKUP), "");
                    return !stringRedisTemplate.hasKey(originalKey); // 키가 존재하는 경우
                })
                .map(stringRedisTemplate.opsForValue()::getAndDelete) // 조회 후 삭제
                .filter(Objects::nonNull)
                .map(value -> StrUtils.fromJson(value, clazz))
                .toList();
    }

}
