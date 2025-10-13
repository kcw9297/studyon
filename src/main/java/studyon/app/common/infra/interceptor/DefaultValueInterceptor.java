package studyon.app.common.infra.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * 기본 값 삽입을 위한 인터셉터 클래스
 * @version 1.0
 * @author kcw97
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultValueInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ipAddress = getClientIp(request);

        request.setAttribute("ipAddress", ipAddress);
        request.setAttribute("loginMemberEmail", ""); // 실제로 redis 내 회원 정보를 삽입해야 함
        return true;
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

}
