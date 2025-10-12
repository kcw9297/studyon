package studyon.app.common.infra.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailUtils {

    public static final String FROM_USERNAME = "STUDYON";
    public static final String VERIFY_SUBJECT = "[STUDYON] 이메일 인증 요청입니다.";
    public static final String NOTICE_PURCHASE_SUBJECT = "[STUDYON] 강의 구매 완료 안내 메일입니다.";
    public static final String CID_LOGO = "logo";
    public static final ClassPathResource IMAGE_LOGO = new ClassPathResource("static/images/cat.gif");


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


    public static String createNoticeContent(Long orderId, String lectureName, Long purchasePrice) {
        return """
            <html>
            </html>
        """;
    }


    // helper 설정 (메일 작성)
    public static void setHelper(String from, String to, MimeMessageHelper helper, String content) throws MessagingException, UnsupportedEncodingException {
        helper.setFrom(from, FROM_USERNAME); // 발신자
        helper.setTo(to); // 수신자
        helper.setSubject(MailUtils.VERIFY_SUBJECT);
        helper.setText(content, true); // HTML 형식 허용
        if (MailUtils.IMAGE_LOGO.exists()) helper.addInline(MailUtils.CID_LOGO, MailUtils.IMAGE_LOGO); // 로고 이미지 삽입
    }

}
