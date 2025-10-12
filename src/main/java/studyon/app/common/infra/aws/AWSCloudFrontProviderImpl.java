package studyon.app.common.infra.aws;

import com.amazonaws.services.cloudfront.CloudFrontCookieSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.AppProfile;
import studyon.app.common.exception.common.ManagerException;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Profile(AppProfile.PROD)
@Component
@RequiredArgsConstructor
public class AWSCloudFrontProviderImpl implements AWSCloudFrontProvider {

    private final AWSSecretProvider secretProvider;

    private static final String COOKIE_HEADER =
            "%s=%s; Path=/; Domain=studyon.o-r.kr; Secure; HttpOnly; SameSite=None; Max-Age=%s";

    @Value("${prod.aws.signed-cookie.expire-min}")
    private Integer expireMin;

    @Value("${prod.aws.domain.cloudfront}")
    private String cloudFrontDomain;

    @Value("${prod.aws.secret.cloudfront}")
    private String cloudFrontSecretName;

    private String keyPairId; // CloudFront Public Key pair ID
    private PrivateKey privateKey; // CloudFront Private Key


    // 초기화 메소드
    @PostConstruct
    public void init() {

        log.warn("AWSManagerImpl init");

        try {
            Map<String, String> secrets = secretProvider.getSecret(cloudFrontSecretName);
            keyPairId = secrets.get("cloudfront_key_pair_id");

            // 헤더 및 공백 제거
            String privateKeyPEM = cleanSecretKeyPEM(secrets.get("cloudfront_private_key"));

            privateKey = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyPEM)));

        } catch (Exception e) {
            throw new ManagerException("AWSManager 초기화 실패!", e);
        }
    }

    public String cleanSecretKeyPEM(String pem) {

        return pem.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
    }


    @Override
    public void setSignedCookies(HttpServletResponse response) {

        // 유효 시간 설정 (5분)
        Date expires = new Date(System.currentTimeMillis() + 1000L * 60 * expireMin);

        // CloudFront Signed Cookie 생성
        CloudFrontCookieSigner.CookiesForCustomPolicy cookies = getSignedCookies(expires);

        // SignedCookie 3개 전달
        addHeader(response, cookies.getKeyPairId().getKey(), cookies.getKeyPairId().getValue());
        addHeader(response, cookies.getSignature().getKey(), cookies.getSignature().getValue());
        addHeader(response, cookies.getPolicy().getKey(), cookies.getPolicy().getValue());
    }

    // SignedCookie 생성
    private CloudFrontCookieSigner.CookiesForCustomPolicy getSignedCookies(Date expires) {

        /*
            쿠키 정책 JSON 문장 확인 (일부라도 형식에 맞지 않는 값이 전달되면 이미지나 영상이 보이지 않음
            정상 출력 예시 : {"Statement": [{"Resource":"your_domain/*","Condition":{"DateLessThan":{"AWS:EpochTime":1759858862},"IpAddress":{"AWS:SourceIp":"0.0.0.0/0"}}}]}
         */

        /*
        String policy = new String(Base64.getUrlDecoder().decode(cookies.getPolicy().getValue()));
        System.out.println("Decoded Policy:\n" + policy);
        */

        return CloudFrontCookieSigner.getCookiesForCustomPolicy(
                SignerUtils.Protocol.https,
                cloudFrontDomain,
                privateKey,
                "*", // 허용 범위 경로
                keyPairId,      // Public Key ID
                expires,        // 만료 시점
                null,           // activeFrom
                "0.0.0.0/0"     // 허용 IP 범위
        );
    }

    // 쿠키 헤더 삽입
    private void addHeader(HttpServletResponse response, String key, String value) {
        response.addHeader("Set-Cookie", COOKIE_HEADER.formatted(key, value, 60 * expireMin));
    }
}


