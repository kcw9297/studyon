package studyon.app.infra.cache.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import studyon.app.infra.cache.CacheUtils;
import studyon.app.infra.mail.dto.MailVerifyRequest;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.common.enums.Cache;
import studyon.app.common.utils.StrUtils;

import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class RedisCacheManager implements CacheManager {

    public static final int MAX_RECENT_SEARCH_KEYWORD = 10; // 최대 최근 검색어 개수

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public void removeKey(String key) {
        stringRedisTemplate.delete(key);
    }


    @Override
    public void recordLogin(Long memberId, String sessionId) {

        // [1] key
        String key = CacheUtils.createIdKey(Cache.MEMBER_LOGIN, memberId);

        // [2] Redis Set 자료형으로 저장
        stringRedisTemplate.opsForSet().add(key, sessionId);

        // [3] 공용 캐시에도, 로그인 회원 정보 기록
        String value = CacheUtils.createCommonLoginValue(memberId);
        stringRedisTemplate.opsForSet().add(Cache.COMMON_LOGIN.getBaseKey(), value);
    }


    @Override
    public void saveProfile(MemberProfile profile) {

        // [1] key, json 직렬화 문자열 생성
        String key = CacheUtils.createIdKey(Cache.MEMBER_PROFILE, profile.getMemberId());
        String json = StrUtils.toJson(profile); // JSON 직렬화된 회원 정보

        // [2] Redis Value 자료형으로 저장
        stringRedisTemplate.opsForValue().set(key, json);
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
    public boolean recordVerifyMail(MailVerifyRequest mailVerifyRequest, String sessionId) {

        // [1] Key
        String key = CacheUtils.createIdKey(Cache.COMMON_VERIFY_MAIL, sessionId);

        // [2] 이미 중복되는 값이 있는지 확인
        // 존재 시 false 반환 (다시 시도하도록 유도)
        if (stringRedisTemplate.hasKey(key)) return false;

        // [3] 인증 정보를 직렬화 후 저장
        // 정상적으로 생성 요청이 전달되었고 (결과가 null 이 아님) 1개의 원소가 잘 삽입된 경우 true (범용적인 사용을 위해 삭제 기간은 길게 둠)
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, mailVerifyRequest, mailVerifyRequest.getExpiration().plusMinutes(30));
        return Objects.equals(result, true);
    }


    @Override
    public MemberProfile getProfile(Long memberId) {

        // [1] Redis 내 Hash 자료형으로 저장된 MemberProfile 데이터 조회 (Map 형태)
        String key = CacheUtils.createIdKey(Cache.MEMBER_PROFILE, memberId);

        // [2] 데이터 조회 및 역직렬화 후 반환
        return StrUtils.fromJson(stringRedisTemplate.opsForValue().get(key), new TypeReference<>() {});
    }


    @Override
    public List<String> getLatestSearchList(Long memberId) {

        // [1] key
        String key = CacheUtils.createIdKey(Cache.MEMBER_LATEST_SEARCH, memberId);

        // [2] 검색 리스트 반환 (0번째 인덱스 부터, 최대 수용 개수까지)
        return stringRedisTemplate.opsForList().range(key, 0L, -1L);
    }


    @Override
    public MailVerifyRequest getMailRequest(String sessionId) {

        // [1] Redis 내 Hash 자료형으로 저장된 MemberProfile 데이터 조회 (Map 형태)
        String key = CacheUtils.createIdKey(Cache.COMMON_VERIFY_MAIL, sessionId);

        // [2] 데이터 조회 및 역직렬화 후 반환
        return StrUtils.fromJson(stringRedisTemplate.opsForValue().get(key), new TypeReference<>() {});
    }

}
