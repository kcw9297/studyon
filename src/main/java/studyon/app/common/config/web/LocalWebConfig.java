package studyon.app.common.config.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import studyon.app.common.constant.AppProfile;
import studyon.app.common.infra.interceptor.LocalInterceptor;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * WebMvcConfig 관련 설정 (local)
 */
@Profile(AppProfile.LOCAL)
@Configuration
@RequiredArgsConstructor
public class LocalWebConfig implements WebMvcConfigurer {

    private final LocalInterceptor localInterceptor;

    @Value("${local.file.handler}")
    private String localFileHandler;

    @Value("${local.file.dir}")
    private String localFileDir;

    // 사용 Interceptor 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor).order(2);
    }


    /*
        local 환경에서 파일 경로 설정

        [예시]
        handler : /local/**
        locations : file:/C:/PROJECT_FILE
        view(jsp) 에서 파일 접근 : http://localhost:8080/local/member/cat.gif
        실제 파일 경로 : C:/PROJECT_FILE/member/cat.gif
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("%s/**".formatted(localFileHandler))
                .addResourceLocations("file:/%s/".formatted(localFileDir));
    }
}
