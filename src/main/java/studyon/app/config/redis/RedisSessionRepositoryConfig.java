package studyon.app.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.SaveMode;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

import java.time.Duration;

/**
 *
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisSessionRepositoryConfig implements SessionRepositoryCustomizer<RedisIndexedSessionRepository> {

    @Value("${spring.session.timeout}")
    private Duration timeout;

    @Value("${spring.session.redis.namespace}")
    private String namespace;

    @Override // RedisIndexedSessionRepository customize
    public void customize(RedisIndexedSessionRepository sessionRepository) {
        sessionRepository.setDefaultMaxInactiveInterval(timeout);
        sessionRepository.setRedisKeyNamespace(namespace);
        sessionRepository.setFlushMode(FlushMode.ON_SAVE);
        sessionRepository.setSaveMode(SaveMode.ON_SET_ATTRIBUTE);
    }
}
