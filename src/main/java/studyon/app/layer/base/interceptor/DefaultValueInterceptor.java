package studyon.app.layer.base.interceptor;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import studyon.app.common.constant.Env;
import studyon.app.common.utils.EnvUtils;
import studyon.app.common.utils.SecurityUtils;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.aws.AWSCloudFrontProvider;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member.service.MemberService;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-15) : kcw97 local/prod 통합
 */

/**
 * 기본 값 삽입을 위한 인터셉터 클래스
 * @version 1.1
 * @author kcw97
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultValueInterceptor implements HandlerInterceptor {

    private final Environment env;
    private final ObjectProvider<AWSCloudFrontProvider> awsCloudFrontProviderProvider;
    private final CacheManager cacheManager;
    private final MemberService memberService;

    @Value("${file.domain}")
    private String fileDomain;

    // 프로필 판별 값
    private boolean isLocal;
    private boolean isProd;

    // 빈 초기화 후 앱 시작 전 호출
    @PostConstruct
    private void init() {
        this.isLocal = EnvUtils.hasProfile(env, Env.PROFILE_LOCAL);
        this.isProd = EnvUtils.hasProfile(env, Env.PROFILE_PROD);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 사용자 URL 요청인 경우에만 출력 (클라이언트 요청 당 1번만 작동하도록)
        if (handler instanceof HandlerMethod &&
                Objects.equals(request.getDispatcherType(), DispatcherType.REQUEST)) {

            // 공통 로직 수행
            doCommon(request);

            // 프로필 유형에 따라 로직 수행
            if (isLocal) doLocal(request, response);
            else if (isProd) doProd(request, response);
        }


        // 컨트롤러에 정상 접근하도록 true 반환 (false 반환 시 접근 실패)
        return true;
    }

    // 공통 로직
    private void doCommon(HttpServletRequest request) {

        boolean isLogin = SecurityUtils.isLogin();
        log.warn("isLogin = {}", isLogin);
        request.setAttribute("isLogin", isLogin);
        request.setAttribute("fileDomain", fileDomain);
        request.setAttribute("ipAddress", getClientIp(request));
        if (isLogin) checkMemberProfile(request);
    }

    // 회원 프로필 조회
    private void checkMemberProfile(HttpServletRequest request) {

        // [1] 프로필 조회
        Long memberId = SessionUtils.getMemberId(request);
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        log.warn("profile = {}", profile);

        // [2] 프로필이 없는 경우, 회원정보 조회 후 새로운 회원 프로필 정보 삽입
        if (Objects.isNull(profile)) {
            MemberProfile newProfile = memberService.readProfile(memberId);
            log.warn("newProfile = {}", newProfile);
            cacheManager.saveProfile(memberId, newProfile);
        }
    }

    // 사용자의 실제 IP 추출
    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        String clientIp = (Objects.nonNull(forwarded)) ? forwarded.split(",")[0].trim() : request.getRemoteAddr();
        return getIPv4ClientIp(clientIp);
    }

    // IPv6 -> IPv4 변환
    private String getIPv4ClientIp(String clientIp) {
        try {

            // [1] ip 형태 구분을 위한 객체 생성
            InetAddress inet = InetAddress.getByName(clientIp);

            // [2] 유형 구분 후 변환 및 반환
            // Local 환경에서 IPv6 주소 변환
            if ("0:0:0:0:0:0:0:1".equals(clientIp) || "::1".equals(clientIp)) return "127.0.0.1";

            // IPv4-mapped IPv6 주소 (::ffff:x.x.x.x) -> x.x.x.x
            if (clientIp.startsWith("::ffff:")) return clientIp.substring(7);

            // [순수 IPv6 는 변환 불가 -> ex. UNKNOWN_IPv6(2404:6800:4001::200e)
            if (inet instanceof Inet6Address) return "UNKNOWN_IPv6(%s)".formatted(clientIp);

            // IPv4 형태는 처리하지 않고 그대로 반환
            return inet.getHostAddress();

        } catch (UnknownHostException e) {
            return clientIp;
        }
    }

    // "local" 프로필 수행
    private void doLocal(HttpServletRequest request, HttpServletResponse response) {
    }

    // "prod" 프로필 수행
    private void doProd(HttpServletRequest request, HttpServletResponse response) {

        // [1] cloudFrontProvider 빈 추출
        AWSCloudFrontProvider cloudFrontProvider =
                awsCloudFrontProviderProvider.getIfAvailable();

        // 만약 값이 없는 경우 (빈 주입이 비정상 수행된 경우)
        if (Objects.isNull(cloudFrontProvider)) {
            log.error(StrUtils.createLogStr(this.getClass(), "PROD 빈 주입이 정상 수행되지 않았습니다!"));
            return;
        }

        // [2] signed cookie 삽입
        cloudFrontProvider.setSignedCookies(response);
    }


}
