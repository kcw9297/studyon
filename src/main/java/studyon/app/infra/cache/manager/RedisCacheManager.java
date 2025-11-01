package studyon.app.infra.cache.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import studyon.app.infra.cache.RedisUtils;
import studyon.app.common.enums.Cache;
import studyon.app.common.utils.StrUtils;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

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
    public void recordRecentKeyword(Long memberId, String keyword) {

        // [1] Key
        String key = RedisUtils.createIdKey(Cache.MEMBER_LATEST_SEARCH, memberId);

        // [2] 중복 키워드 존재 시 제거 후 검색어 기록
        stringRedisTemplate.opsForList().remove(key, 0, keyword);
        stringRedisTemplate.opsForList().leftPush(key, keyword); // 리스트 맨 처음에 삽입

        // [3] 최대 저장 검색어 개수를 초과하는 경우, 맨 마지막 검색어 제거
        stringRedisTemplate.opsForList().trim(key, 0, 4); // 최대 5개
    }


    @Override
    public List<String> getRecentKeywords(Long memberId) {

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
}
