package studyon.app.config.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import studyon.app.common.constant.AppProfile;
import studyon.app.layer.base.interceptor.ProdInterceptor;

/**
 * WebMvcConfig 관련 설정 (prod)
 */
@Profile(AppProfile.PROD)
@Configuration
@RequiredArgsConstructor
public class ProdWebConfig implements WebMvcConfigurer {

    private final ProdInterceptor prodInterceptor;

    // 사용 Interceptor 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(prodInterceptor).order(2);
    }

}
