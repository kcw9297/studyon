package studyon.app.infra.mail.manager;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import studyon.app.infra.mail.exception.VerifyCodeException;
import studyon.app.infra.mail.exception.VerifyException;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.mail.dto.MailVerifyRequest;

import java.io.UnsupportedEncodingException;
import java.time.Duration;

/**
 * 메일 발송 구현체 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class MailManagerImpl implements MailManager {

    private final JavaMailSender mailSender;
    private final CacheManager cacheManager;

    public static final String FROM_USERNAME = "STUDYON";
    public static final String VERIFY_SUBJECT = "[STUDYON] 이메일 인증 요청입니다.";
    public static final String NOTICE_PURCHASE_SUBJECT = "[STUDYON] 강의 구매 완료 안내 메일입니다.";
    public static final String CID_LOGO = "logo";
    public static final ClassPathResource IMAGE_LOGO = new ClassPathResource("static/img/gif/cat.gif");

    @Value("${spring.mail.username}")
    private String from;


    @Override
    public MailVerifyRequest sendVerifyCode(String to, Duration expiration, String sessionId) {

        try {
            // [1] 메세지 객체 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = getMimeMessageHelper(message);

            // [2] 작성을 위한 정보 생성
            String verifyCode = StrUtils.createRandomNumString(8); // 8자리 인증 코드
            String content = createVerifyContent(verifyCode, expiration);

            // [3] 이메일 내용 작성
            setHelper(from, to, helper, content);

            // [4] 발송 정보를 담은 메일 요청 객체 생성
            MailVerifyRequest request = createRequest(to, expiration, verifyCode, sessionId);
            mailSender.send(message);

            // [5] 요청 객체 반환
            return request;

        } catch (VerifyCodeException | VerifyException e) {
            throw e;

        } catch (Exception e) {
            throw new VerifyException("이메일 전송 실패! 잠시 후 다시 시도해 주세요", e);
        }
    }


    // 인증 요청을 담은 메일 요청객체 생성
    private MailVerifyRequest createRequest(String to, Duration expiration, String verifyCode, String sessionId) {

        // [1] 요청 객체 생성
        MailVerifyRequest request = MailVerifyRequest.createVerifyRequest(to, verifyCode, expiration);

        // [2] 만일 다른 인증 회원과 코드가 중복된 경우, 예외 반환 (다시 인증을 요청하도록 유도)
        if (!cacheManager.recordVerifyMail(sessionId, request))
            throw new VerifyException("메일 전송에 실패했습니다! 다시 시도해 주세요");

        return request;
    }



    @Override
    public void send(String to, String subject, String content) {

        try {
            // [1] 메세지 객체 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = getMimeMessageHelper(message);

            // [2] 이메일 내용 작성
            setHelper(from, to, helper, content);

            // [3] 이메일 발송
            mailSender.send(message);

        } catch (Exception e) {
            throw new VerifyException("이메일 전송 실패! 잠시 후 다시 시도해 주세요", e);
        }
    }

    public static MimeMessageHelper getMimeMessageHelper(MimeMessage message) throws MessagingException {
        return new MimeMessageHelper(message, true, "UTF-8");
    }


    public static String createVerifyContent(String verifyCode, Duration expiration) {

        return """
            <html>
                <body style='font-family: Arial, sans-serif;'>
                    <img src='cid:%s' alt='로고' style='width: 200px; display: block; margin-bottom: 20px;'/>
                    <div style='max-width: 600px; margin: 0 auto; padding: 20px;'>
                    <h1>이메일 인증 요청</h1>
                    <p>아래의 인증 코드를 입력하여 이메일 인증을 완료해주세요.</p>
                    <div style='background-color: #f4f4f4; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px;'>
                      %s
                    </div>
                    <p>이 코드는 %s분간 유효합니다.</p>
                </body>
            </html>
        """.formatted(CID_LOGO, verifyCode, String.valueOf(expiration.toMinutes()));
    }


    public void setHelper(String from, String to, MimeMessageHelper helper, String content) throws MessagingException, UnsupportedEncodingException {
        helper.setFrom(from, FROM_USERNAME); // 발신자
        helper.setTo(to); // 수신자
        helper.setSubject(VERIFY_SUBJECT);
        helper.setText(content, true); // HTML 형식 허용
        if (IMAGE_LOGO.exists()) helper.addInline(CID_LOGO, IMAGE_LOGO); // 로고 이미지 삽입
    }
}
