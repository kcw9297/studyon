package studyon.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * 기타 간단한 설정 및 빈을 등록하는 설정 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Configuration
@EnableRetry // 재시도 기능 활성화
public class AppConfig {

}
