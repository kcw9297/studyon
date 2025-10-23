package studyon.app.infra.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import studyon.app.common.enums.Entity;
import studyon.app.infra.cache.CacheUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;

import java.util.Objects;

/**
 * Redis DB 내에서 발생한 이벤트(발행 메세지) 처리 클래스 (만료, 삭제 이벤트 등..)
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisEventListener implements MessageListener {

    private final FileManager fileManager;
    private final CacheManager cacheManager;

    private static final String EXPIRED = "expired";
    private static final String DEL = "del";

    @Override
    public void onMessage(Message message, byte[] pattern) {

        // [1] 이벤트가 발생한 key 값 & 이벤트 유형 조회
        String targetKey = new String(message.getBody());
        String eventType = new String(pattern);
        log.warn("key = {}, eventType = {}", targetKey, eventType);

        /*
        // [2] id 값 분리
        String[] split = targetKey.split(":");
        String id = split[split.length - 1];

        // [3] 확인에 필요한 key 생성
        String lectureQuestionTempKey = CacheUtils.createTempKey(Entity.LECTURE_QUESTION.getName(), id); // 강의질문 임시 키

        // 임시파일 삭제 요청인 경우, 해당 키의
        if (Objects.equals(targetKey, lectureQuestionTempKey)) {
            log.warn(" 임시파일 제거");

            String backupKey = CacheUtils.createBackupKey(lectureQuestionTempKey);
            List<String> tempFileNames = cacheManager.getAndRemoveBackupKey(backupKey, );
            log.warn("tempFileNames = {}", tempFileNames);
            tempFileNames.forEach(tempFileName -> fileManager.remove(tempFileName, Entity.TEMP));


        }
        */


        // 만료회원 로그인 목록에서 제거하기

        /*
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
            stringRedisTemplate.opsForSet().remove(Cache.CURRENT_LOGIN.getBaseKey(), commonLoginValue);
         */
    }
}
