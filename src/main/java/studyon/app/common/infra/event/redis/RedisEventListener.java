package studyon.app.common.infra.event.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Redis DB 내에서 발생한 이벤트(발행 메세지) 처리 클래스 (만료, 삭제 이벤트 등..)
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisEventListener implements MessageListener {

    private static final String EXPIRED = "expired";
    private static final String DEL = "del";

    @Override
    public void onMessage(Message message, byte[] pattern) {

        // 이벤트가 발생한 key 값 & 이벤트 유형 조회
        String key = new String(message.getBody());
        String eventType = new String(pattern);
        log.warn("key = {}, eventType = {}", key, eventType);
    }
}
