package studyon.app.layer.base.interceptor;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import studyon.app.common.constant.AppProfile;
import studyon.app.common.utils.AppUtils;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.aws.AWSCloudFrontProvider;

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

    @Value("${file.domain}")
    private String fileDomain;

    // 프로필 판별 값
    private boolean isLocal;
    private boolean isProd;

    // 빈 초기화 후 앱 시작 전 호출
    @PostConstruct
    private void init() {
        this.isLocal = AppUtils.hasProfile(AppProfile.LOCAL, env);
        this.isProd = AppUtils.hasProfile(AppProfile.PROD, env);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // [1] 활성화 프로필 추출 (사용하는 프로필은 1개)
        String activeProfile = env.getActiveProfiles()[0];

        // [2] 프로필 유형에 따라 로직 수행
        doCommon(request);
        if (isLocal) doLocal(request, response);
        else if (isProd) doProd(request, response);
        return true;
    }


    private void doCommon(HttpServletRequest request) {
        request.setAttribute("fileDomain", fileDomain);
        request.setAttribute("ipAddress", getClientIp(request));
        request.setAttribute("loginMemberEmail", ""); // 실제로 redis 내 회원 정보를 삽입해야 함
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


    private void doLocal(HttpServletRequest request, HttpServletResponse response) {
    }


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
