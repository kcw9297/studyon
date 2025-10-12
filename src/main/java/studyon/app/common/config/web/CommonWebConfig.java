package studyon.app.common.config.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import studyon.app.common.infra.interceptor.DefaultValueInterceptor;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CommonWebConfig implements WebMvcConfigurer {

    private final DefaultValueInterceptor defaultValueInterceptor;

    // 사용 Interceptor 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultValueInterceptor).order(1);
    }

}
