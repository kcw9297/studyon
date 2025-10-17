package studyon.app.infra.mail.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailVerifyRequest {

    private String to;
    private String verifyCode;
    private Duration expiration;
    private LocalDateTime expiredAt;

    public MailVerifyRequest(String to, String verifyCode, Duration expiration, LocalDateTime expiredAt) {
        this.to = to;
        this.verifyCode = verifyCode;
        this.expiration = expiration;
        this.expiredAt = expiredAt;
    }

    // 인증 메일요청 생성
    public static MailVerifyRequest createVerifyRequest(String to, String verifyCode, Duration expiration) {
        return new MailVerifyRequest(to, verifyCode, expiration, LocalDateTime.now().plus(expiration));
    }

    // 인증 코드 확인
    public boolean checkVerifyCode(String verifyCode) {
        return Objects.equals(verifyCode, this.verifyCode);
    }
}
