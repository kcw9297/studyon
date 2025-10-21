package studyon.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.FlushMode;
import org.springframework.session.SaveMode;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import studyon.app.common.constant.Env;
import studyon.app.infra.redis.RedisEventListener;

import java.time.Duration;
import java.util.Objects;

/**
 *
 */

@Slf4j
@Configuration
@EnableCaching // SpringCache 활성화 (Redis)
@EnableRedisIndexedHttpSession // Session Event 수신 가능
@RequiredArgsConstructor
public class RedisConfig implements SessionRepositoryCustomizer<RedisIndexedSessionRepository> {

    private static final String KEY_EVENT_EXPIRED = "__keyevent@*__:expired";   // 만료 이벤트
    private static final String KEY_EVENT_DEL = "__keyevent@*__:del";           // 삭제 이벤트
    private static final String ON_MESSAGE = "onMessage";

    private final ObjectMapper objectMapper;
    private final RedisEventListener redisEventListener;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.data.redis.ssl.enabled}")
    private boolean ssl;

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

    @Bean // HttpSession 을 Redis 내 저장 시, 사용하는 직렬화 클래스 지정
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new JdkSerializationRedisSerializer();
    }

    @Bean // RedisTemplate customize
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        return redisTemplate;
    }


    @Bean // connector 구현체로 Lettuce 사용 (가장 좋은 성능)
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        if (Objects.nonNull(password) && !password.isBlank()) config.setPassword(password);

        // SSL 설정 추가
        if (ssl) {
            LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                    .useSsl()  // ssl 활성화
                    .build();

            return new LettuceConnectionFactory(config, clientConfig);

        } else {
            return new LettuceConnectionFactory(config);
        }

    }


    @Bean // 메세지 리스너 등록
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(redisEventListener, ON_MESSAGE);
    }


    @Bean   // Listener 서용을 휘한 ListenerContainer 등록
    @Primary // springSessionRedisMessageListenerContainer 내에서 RedisMessageListenerContainer 둥록을 시도하기 때문에, 우선순위 부여
    public RedisMessageListenerContainer redisMessageListenerContainer() {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerAdapter(), new PatternTopic(KEY_EVENT_EXPIRED));
        container.addMessageListener(messageListenerAdapter(), new PatternTopic(KEY_EVENT_DEL));

        container.setErrorHandler(e -> {
            log.error("Redis 메시지 리스너 오류 발생", e);
        });

        return container;
    }


    @Bean // CONFIG 비활성화
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }


}
