package studyon.app.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import studyon.app.common.constant.Env;
import studyon.app.common.utils.EnvUtils;
import studyon.app.layer.base.interceptor.DefaultValueInterceptor;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-15) : kcw97 Local/Prod 설정 통합
 */

/**
 * WebMvcConfigurer 설정 클래스
 * @version 1.1
 * @author kcw97
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final DefaultValueInterceptor defaultValueInterceptor;
    private final Environment env;

    @Value("${file.handler}")
    private String fileHandler;

    @Value("${file.dir}")
    private String fileDir;

    // 프로필 판별 값
    private boolean isLocal;
    private boolean isProd;

    // 빈 초기화 후 앱 시작 전 호출
    @PostConstruct
    private void init() {
        this.isLocal = EnvUtils.hasProfile(env, Env.PROFILE_LOCAL);
        this.isProd = EnvUtils.hasProfile(env, Env.PROFILE_PROD);
    }

    // 사용 Interceptor 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultValueInterceptor).order(1);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 로컬인 경우만 등록
        if (isLocal)
            registry.addResourceHandler("%s/**".formatted(fileHandler))
                    .addResourceLocations("file:/%s/".formatted(fileDir));
    }


}
