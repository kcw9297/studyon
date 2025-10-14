package studyon.app.infra.mail.manager;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import studyon.app.common.exception.common.VerifyCodeException;
import studyon.app.common.exception.common.VerifyException;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.mail.MailUtils;
import studyon.app.infra.mail.dto.MailVerifyRequest;

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

    @Value("${spring.mail.username}")
    private String from;


    @Override
    public MailVerifyRequest sendVerifyCode(String to, Duration expiration, String sessionId) {

        try {
            // [1] 메세지 객체 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = MailUtils.getMimeMessageHelper(message);

            // [2] 작성을 위한 정보 생성
            String verifyCode = StrUtils.createRandomNumString(8); // 8자리 인증 코드
            String content = MailUtils.createVerifyContent(verifyCode, expiration);

            // [3] 이메일 내용 작성
            MailUtils.setHelper(from, to, helper, content);

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
        if (!cacheManager.recordVerifyMail(request, sessionId))
            throw new VerifyException("메일 전송에 실패했습니다! 다시 시도해 주세요");

        return request;
    }



    @Override
    public void send(String to, String subject, String content) {

        try {
            // [1] 메세지 객체 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = MailUtils.getMimeMessageHelper(message);

            // [2] 이메일 내용 작성
            MailUtils.setHelper(from, to, helper, content);

            // [3] 이메일 발송
            mailSender.send(message);

        } catch (Exception e) {
            throw new VerifyException("이메일 전송 실패! 잠시 후 다시 시도해 주세요", e);
        }
    }
}
