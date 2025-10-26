package studyon.app.infra.mail;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-26) : kcw97 DTO의존성 제거, 보내는 메일 유형 별로 메소드 추가
 */


import java.time.Duration;

/**
 * 메일 전송 처리
 * @version 1.1
 * @author kcw97
 */
public interface MailManager {

    /**
     * 인증 URL 제공 이메일 발송
     * @param to         수신자 이메일
     * @param verifyUrl 인증 URL
     * @param expiration 인증 만료 시간
     */
    void sendVerifyUrl(String to, String verifyUrl, Duration expiration);

    /**
     * 인증 코드 제공 이메일 발송
     * @param to         수신자 이메일
     * @param expiration 인증 만료 시간
     */
    void sendVerifyCode(String to, String verifyCode, Duration expiration);


}
