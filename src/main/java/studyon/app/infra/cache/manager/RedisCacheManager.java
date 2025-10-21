package studyon.app.infra.cache.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import studyon.app.infra.cache.CacheUtils;
import studyon.app.common.enums.Cache;
import studyon.app.common.utils.StrUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class RedisCacheManager implements CacheManager {

    public static final int MAX_RECENT_SEARCH_KEYWORD = 10; // 최대 최근 검색어 개수

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
    public void removeLogout(Long memberId, String sessionId) {
        stringRedisTemplate.opsForSet().remove(Cache.CURRENT_LOGIN.getBaseKey(), sessionId);
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
    public void recordEditorTempFile(String sessionId, String tempFileName) {

        // [1] key
        String key = CacheUtils.createIdKey(Cache.EDITOR_TEMP, sessionId);
        String backupKey = CacheUtils.createBackupKey(key);

        // [2] Redis List 자료형으로 저장
        stringRedisTemplate.opsForList().rightPush(key, tempFileName);
        stringRedisTemplate.expire(key, Duration.ofSeconds(30)); // 30초뒤 만료

        // [3] 백업 데이터 저장 (만료/삭제 시 처리 목적)
        stringRedisTemplate.opsForList().rightPush(backupKey, tempFileName);
        stringRedisTemplate.expire(key, Duration.ofSeconds(300)); // 원본보다 더 긴 만료시간
    }


    @Override
    public List<String> getAndRemoveAllEditorTempFiles(String sessionId) {

        // [1] key
        String key = CacheUtils.createIdKey(Cache.EDITOR_TEMP, sessionId);
        List<String> members = stringRedisTemplate.opsForList().range(key, 0, -1);

        // [2] key 삭제 및 반환
        stringRedisTemplate.delete(key);
        return Objects.isNull(members) ?  new ArrayList<>() : members;
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


}
