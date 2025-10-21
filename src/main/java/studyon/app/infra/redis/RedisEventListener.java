package studyon.app.infra.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import studyon.app.common.enums.Cache;
import studyon.app.common.enums.Entity;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;

import java.util.List;
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

        // 이벤트가 발생한 key 값 & 이벤트 유형 조회
        String key = new String(message.getBody());
        String eventType = new String(pattern);
        log.warn("key = {}, eventType = {}", key, eventType);

        // 임시파일 삭제 요청인 경우, 해당 키의
        if (key.startsWith(Cache.EDITOR_TEMP.getBaseKey())) {
            log.warn("에디터 임시파일 제거");
            String[] split = key.split(":");
            String sessionId = split[split.length - 1];

            List<String> tempFileNames = cacheManager.getAndRemoveAllEditorTempFiles(sessionId);
            log.warn("tempFileNames = {}", tempFileNames);
            tempFileNames.forEach(tempFileName -> fileManager.remove(tempFileName, Entity.TEMP));


        }
    }
}
