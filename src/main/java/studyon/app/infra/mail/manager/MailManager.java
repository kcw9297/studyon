package studyon.app.infra.mail.manager;

import studyon.app.infra.mail.dto.MailVerifyRequest;

import java.time.Duration;

/**
 * 메일 전송 및 인증 처리
 * @version 1.0
 * @author kcw97
 */
public interface MailManager {

    /**
     * 인증 코드 이메일 발송
     * @param to 수신자 이메일
     * @param expiration 만료 시간
     * @param sessionId 요청 세션 아이디
     * @return 발송한 이메일 요청 정보 반환
     */
    MailVerifyRequest sendVerifyCode(String to, Duration expiration, String sessionId);

    /**
     * 이메일 발송
     * @param to 수신자 이메일
     * @param subject 메일 제목
     * @param content 메일 본문
     */
    void send(String to, String subject, String content);

}
