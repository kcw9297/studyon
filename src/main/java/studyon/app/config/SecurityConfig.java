package studyon.app.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import studyon.app.common.constant.Env;
import studyon.app.common.constant.Param;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.Role;
import studyon.app.infra.security.handler.*;
import studyon.app.infra.security.provider.CustomDaoAuthenticationProvider;
import studyon.app.infra.security.service.CustomNormalUserService;
import studyon.app.infra.security.service.CustomSocialUserService;
import studyon.app.layer.domain.member.repository.MemberRepository;

import java.util.Objects;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // (@PreAuthorize, @PostAuthorize 활성화)
@RequiredArgsConstructor
public class SecurityConfig {

    // Custom Handler
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomSocialLoginSuccessHandler customSocialLoginSuccessHandler;
    private final CustomSocialLoginFailedHandler customSocialLoginFailedHandler;
    private final CustomNormalLoginSuccessHandler customNormalLoginSuccessHandler;
    private final CustomNormalLoginFailedHandler customNormalLoginFailedHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    // Custom UserService
    //private final CustomNormalUserService customNormalUserService;
    private final CustomSocialUserService customSocialUserService;

    // repository
    private final MemberRepository memberRepository;

    // OAuth2 Social Login URL
    private static final String URL_OAUTH2_AUTHORIZATION = "/oauth2/authorization"; // OAuth2 Authorization URL
    private static final String URL_OAUTH2_REDIRECT = "/login/oauth2/code/*"; // OAuth2 REDIRECT URL

    // Spring Security url pattern
    private static final String AUTH_ALL = Url.AUTH + "/**";
    private static final String AUTH_API_ALL = Url.AUTH_API + "/**";
    private static final String JOIN_ALL = Url.JOIN + "/**";
    private static final String LOGIN_ALL = Url.LOGIN + "/**";
    private static final String OAUTH2_ALL = Url.OAUTH2 + "/**";
    private static final String TEACHER_ALL = Url.TEACHER + "/**"; // 선생님 관리 페이지
    private static final String TEACHERS_ALL = Url.TEACHERS + "/**";
    private static final String TEACHERS_API_ALL = Url.TEACHERS_API + "/**";
    private static final String ADMIN_ALL = Url.ADMIN + "/**";
    private static final String LECTURES_ALL = Url.LECTURES + "/**";
    private static final String WEBSOCKET_ALL = "/ws" + "/**";
    private static final String MYPAGE_ALL = Url.MYPAGE + "/**";
    private static final String FILE_ALL = Url.FILE + "/**";
    private static final String HOME_API_ALL = Url.HOME_API + "/**";
    private static final String PAYMENT_ALL = Url.PAYMENT + "/**";
    private static final String PAYMENTS_ALL = Url.PAYMENTS + "/**";

    // 접근을 모두 허용할 주소 (정적 자원 제외)
    public static final String[] PERMIT_ALL =
            {
                    Url.INDEX, HOME_API_ALL, TEACHERS_API_ALL, // 선생님 API는 컨트롤러 애노테이션으로 세부권한 수정
                    AUTH_ALL, AUTH_API_ALL, LECTURES_ALL, TEACHERS_ALL, TEACHER_ALL, FILE_ALL, WEBSOCKET_ALL
            };

    public static final String[] STUDENT =
            {
                    MYPAGE_ALL, PAYMENT_ALL, PAYMENTS_ALL
            };


    public static final String[] TEACHER =
            {
                    TEACHER_ALL
            };

    public static final String[] ANONYMOUS =
            {
                    OAUTH2_ALL, JOIN_ALL, LOGIN_ALL
            };


    // Spring Security CSRF ignore URL (로그인, 로그아웃은 검증 제외)
    public static final String[] CSRF_IGNORE_URLS = {
            Url.LOGIN_PROCESS, Url.LOGOUT
    };

    // 로그아웃 시 삭제할 쿠키 이름 (세션 쿠키)
    public static final String[] LOGOUT_DELETE_COOKIES = {
            "JSESSIONID", "SESSION"
    };

    // 활성 프로필 (local or prod)
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${file.domain}")
    private String fileDomain;

    // 모든 파일 도메인 허용
    private String fileAll;

    // 초기화 메소드
    @PostConstruct
    public void init() {
        this.fileAll = fileDomain + "/**";
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // [1] Spring Security 기본 설정
        HttpSecurity config = http

                // 에디터 iframe 사용을 위해, SameOrigin 만 허용
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // CSRF 설정 (POST, DELETE, PATCH 등 변경이 발생하는 HTTP Method 는 반드시 검증 포함)
                // AJAX 요청 시에는 반드시 "X-XSRF-TOKEN" 포함해야만 인증 성공 처리)
                //.csrf(csrf -> csrf
                //        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //        .ignoringRequestMatchers(CSRF_IGNORE_URLS) // CSRF 검증 예외 URL
                //)
                .csrf(AbstractHttpConfigurer::disable)

                // CORS 설정 비활성화 (SSR 렌더링 시 프론트와 백엔드 요청이 모두 같은 도메인에서 발생)
                .cors(AbstractHttpConfigurer::disable)

                // custom dao provider
                .authenticationProvider(authenticationProvider())

                // 요청 권한 설정
                .authorizeHttpRequests(authorize -> authorize

                        // 모두 허용하는 정적 자원 주소
                        .requestMatchers(Url.STATIC_RESOURCE_PATHS).permitAll() // 정적 자원 경로 허용
                        .requestMatchers(fileAll).permitAll() // 파일 도메인 허용

                        // 공개 페이지 - 모두 접근 가능
                        .requestMatchers(PERMIT_ALL).permitAll()

                        // 관리자만 접근 가능
                        .requestMatchers(ADMIN_ALL).hasAuthority(Role.ROLE_ADMIN.name())

                        // 선생님만 접근 가능
                        .requestMatchers(TEACHER).hasAuthority(Role.ROLE_TEACHER.name())

                        // 학생만 접근 가능
                        .requestMatchers(STUDENT).hasAuthority(Role.ROLE_STUDENT.name())

                        // 로그인, 회원가입 - 익명 사용자만 접근 가능 (로그아웃 사용자)
                        .requestMatchers(ANONYMOUS).anonymous()

                        // 그 외의 요청은 인증된 사용자만 허용 (로그인 회원에게만)
                        .anyRequest().authenticated()

                        //.anyRequest().permitAll()
                )

                // 일반 로그인 설정
                .formLogin(form -> form
                        .loginPage(Url.LOGIN) // 로그인 페이지
                        .usernameParameter(Param.EMAIL) // 일반 로그인 아이디에 해당하는 필드명
                        .passwordParameter(Param.PASSWORD) // 일반 로그인 패스워드에 해당하는 필드명
                        .loginProcessingUrl(Url.LOGIN_PROCESS) // 로그인 처리 페이지
                        .successHandler(customNormalLoginSuccessHandler) // 로그인 성공처리 핸들러
                        .failureHandler(customNormalLoginFailedHandler) // 로그인 실패처리 핸들러
                )

                /*
                // 일반로그인 회원정보를 조회할 userDetailsService 설정
                .rememberMe(rememberMe -> rememberMe
                        .userDetailsService(customNormalUserService)
                )

                 */

                // 소셜 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .loginPage(Url.LOGIN) // 일반 로그인페이지와 동일
                        .authorizationEndpoint(authorization -> authorization.baseUri(URL_OAUTH2_AUTHORIZATION))
                        .redirectionEndpoint(redirection -> redirection.baseUri(URL_OAUTH2_REDIRECT))
                        .userInfoEndpoint(endpoint -> endpoint.userService(customSocialUserService))
                        .successHandler(customSocialLoginSuccessHandler) // 로그인 성공처리 핸들러
                        .failureHandler(customSocialLoginFailedHandler) // 로그인 실패처리 핸들러
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl(Url.LOGOUT) // 로그아웃 페이지
                        .logoutSuccessHandler(customLogoutHandler) // 커스텀 처리 핸들러
                        .deleteCookies(LOGOUT_DELETE_COOKIES) // Cookie 삭제
                        .invalidateHttpSession(true) // HttpSession 무효화
                        .clearAuthentication(true) // Authentication 인증 객체 삭제
                        .permitAll() // 로그아웃 페이지는 모두 허용
                )

                // 세션 관리 설정
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요 시에만 세션 발급
                        .sessionFixation().migrateSession() // Session Fixation Attack 방지 옵션
                        .maximumSessions(1) // 최대 로그인 세션 개수 1개 (한 사용자는 총 하나의 세션만 가능)
                        .maxSessionsPreventsLogin(false) // 최대 세션 초과 시, 로그인 불허 여부 (현재는 허용)
                        .expiredUrl(Url.LOGIN)
                )


                // 회원 권한이 부족하거나, 비로그인 사용자의 로그인 서비스 접근 예외 처리
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                );


        // [2] 배포 환경 설정
        if (Objects.equals(activeProfile, Env.PROFILE_PROD)) config.redirectToHttps(Customizer.withDefaults()); // HTTPS 강제

        // [3] 공통/프로필별 설정 반영 후 반환
        return config.build();
    }

    @Bean // PasswordEncoder - BCrypt 사용 (SHA 알고리즘보다 훨씬 안전함)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // 일반회원 로그인 처리 AuthenticationProvider 수동 등록 (CustomNormalUserService 수동 생성)
    public AuthenticationProvider authenticationProvider() {
        return new CustomDaoAuthenticationProvider(passwordEncoder(), new CustomNormalUserService(memberRepository));
    }

}