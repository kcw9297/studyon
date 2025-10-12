package studyon.app.common.infra.redis;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Redis MessageListener 사용을 위한 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageListenerStarter {

    private final RedisMessageListenerContainer container;

    @PostConstruct
    public void init() {
        if (Objects.nonNull(container)) {
            container.start();
            log.info("[RedisMessageListenerStarter] RedisMessageListenerContainer start");
        }
    }

    @PreDestroy
    public void cleanup() {
        if (Objects.nonNull(container)) {
            container.stop();
            log.info("[RedisMessageListenerStarter] RedisMessageListenerContainer stop");
        }
    }
}
