package studyon.app.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import studyon.app.infra.redis.RedisEventListener;

/**
 *
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisEventConfig {

    private static final String KEY_EVENT_EXPIRED = "__keyevent@*__:expired";   // 만료 이벤트
    private static final String KEY_EVENT_DEL = "__keyevent@*__:del";           // 삭제 이벤트
    private static final String ON_MESSAGE = "onMessage";

    private final RedisEventListener redisEventListener;
    private final RedisConnectionFactory redisConnectionFactory;

    @Bean // 메세지 리스너 등록
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(redisEventListener, ON_MESSAGE);
    }

    @Bean   // Listener 서용을 휘한 ListenerContainer 등록
    @Primary // springSessionRedisMessageListenerContainer 내에서 RedisMessageListenerContainer 둥록을 시도하기 때문에, 우선순위 부여
    public RedisMessageListenerContainer redisMessageListenerContainer() {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter(), new PatternTopic(KEY_EVENT_EXPIRED));
        container.addMessageListener(messageListenerAdapter(), new PatternTopic(KEY_EVENT_DEL));

        container.setErrorHandler(e -> {
            log.error("Redis 메시지 리스너 오류 발생", e);
        });

        return container;
    }
}
