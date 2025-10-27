package studyon.app.layer.domain.auth.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.constant.Env;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Provider;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.common.utils.EnvUtils;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.mail.MailManager;
import studyon.app.layer.domain.auth.JoinCache;
import studyon.app.layer.domain.member.repository.MemberRepository;

import java.time.Duration;
import java.util.Objects;

/**
 * 캐시 데이터 기반 인증 서비스 제공 구현체
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CacheManager cacheManager;
    private final MailManager mailManager;
    private final MemberRepository memberRepository;

    private final Environment env;

    @Value("${app.domain}")
    private String domain;

    @Value("${server.port}")
    private String port;

    private String currentDomain = ""; // 현재 환경에 따라 변동

    @PostConstruct
    public void init() {

        // [1] 프로필 상태 확인
        boolean isLocal = EnvUtils.hasProfile(env, Env.PROFILE_LOCAL);
        boolean isProd = EnvUtils.hasProfile(env, Env.PROFILE_PROD);

        // [2] 프로필에 따라 도메인 형태 적용
        if (isLocal) currentDomain = "http://localhost:%s".formatted(port);
        if (isProd) currentDomain = "https://%s".formatted(domain);

    }

    @Override
    public void sendPasswordEditEmail(String email) {

        // [1] 활성 상태이면서, 일반 회원인 이메일 조회 (소셜 회원은 이메일이 바뀔 수 있음)
        // 만약 존재하지 않는 경우 인증 요청이 불가능한 대상
        if (!memberRepository.existsByEmailAndProviderAndIsActive(email, Provider.NORMAL, true))
            throw new BusinessLogicException(AppStatus.AUTH_MAIL_NOT_FOUND);

        // [2] 인증 요청 전, 이미 전송된 요청이 있는지 검증
        String token = StrUtils.createUUID();
        Duration expiration = Duration.ofMinutes(5);

        // 이미 인증 요청이 존재하면 중복된 요청이므로 예외 반환
        if (!cacheManager.recordAuthRequest(email, token, expiration, email)) // 이메일 문자열을 내부에 담음
            throw new BusinessLogicException(AppStatus.AUTH_REQUEST_ALREADY_EXIST);

        // [3] 인증 URL 생성 및 이메일 인증요청 발송
        String verifyUrl = "%s%s?token=%s".formatted(currentDomain, Url.AUTH_EDIT_PASSWORD, token);
        mailManager.sendVerifyUrl(email, verifyUrl, expiration);
    }


    @Override
    public void verify(String token) {

        // 검증 수행 - 인증 정보가 존재하지 않는 경우 (TTL 초과) 예외 반환
        if (!cacheManager.isAuthRequestValid(token))
            throw new BusinessLogicException(AppStatus.AUTH_INVALID_REQUEST);
    }


    @Override
    public <T> T verifyAndGetData(String token, Class<T> dataType) {

        // [1] 인증 요청 조회
        T authRequest = cacheManager.getAuthRequest(token, dataType);

        // [2] 인증 요청이 없음 -> 만료되었거나 잘못된 요청 (예외 반환)
        if (Objects.isNull(authRequest))
            throw new BusinessLogicException(AppStatus.AUTH_INVALID_REQUEST);

        return authRequest;
    }


    @Override
    public void removeAuthRequest(String token) {
        cacheManager.removeAuthRequest(token);
    }


    @Override
    public JoinCache getJoinCache() {
        return null;
    }


    @Override
    public void sendJoinEmail(String email) {

    }
}
