package studyon.app.infra.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import studyon.app.common.enums.Cache;
import studyon.app.infra.cache.CacheUtils;
import studyon.app.layer.base.utils.SessionUtils;

import java.util.Objects;

/**
 * HttpSession 만료 시, 해당 이벤트를 처리하는 리스너 클래스
 * @version 1.0
 * @author kcw97
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SessionDestroyedListener implements ApplicationListener<SessionDestroyedEvent> {

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${spring.session.redis.namespace}")
    private String namespace;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {

        // [1] 만료 세션정보 조회
        String sessionId = event.getSessionId();
        String sessionKey = SessionUtils.createSessionKey(namespace, sessionId);
        Object memberIdObj = stringRedisTemplate.opsForHash().get(sessionKey, "sessionAttr:memberId");

        // 회원 번호가 존재하지 않으면, 로직을 수행하지 않음
        if (Objects.isNull(memberIdObj)) return;

        // [2] 필요 정보 추출
        Long memberId = Long.parseLong((String) memberIdObj);
        String memberLoginKey = CacheUtils.createIdKey(Cache.MEMBER_LOGIN, memberId);
        String commonLoginValue = CacheUtils.createCommonLoginValue(memberId);
        log.warn("onApplicationEvent sessionId = {}, memberId = {}", sessionId, memberId);

        // [3] 회원 로그인 목록에서 만료된 id 제거
        stringRedisTemplate.opsForSet().remove(memberLoginKey, sessionId);
        Long remain = stringRedisTemplate.opsForSet().size(memberLoginKey);

        // [4] 만약 로그인 세션이 더 이상 남아있지 않으면, 로그인 회원 목록에서 제거
        if (Objects.equals(remain, 0L))
            stringRedisTemplate.opsForSet().remove(Cache.COMMON_LOGIN.getBaseKey(), commonLoginValue);

    }
}
