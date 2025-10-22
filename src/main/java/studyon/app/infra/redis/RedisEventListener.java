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
    }
}
