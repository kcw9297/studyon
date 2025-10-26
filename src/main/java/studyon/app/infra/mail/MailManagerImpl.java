package studyon.app.infra.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.ManagerException;
import studyon.app.common.utils.StrUtils;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : cacheManager 사용 부분 역할 분리 (여기서 사용하지 않음)
 */

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

    // 메일 전송 상수
    public static final String FROM_USERNAME = "STUDYON";
    public static final String CID_LOGO = "logo";
    public static final ClassPathResource IMAGE_LOGO = new ClassPathResource("static/img/png/logo_login.png");

    // time formatter
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm");

    @Value("${spring.mail.username}")
    private String from;


    @Override
    public void sendVerifyUrl(String to, String verifyUrl, Duration expiration) {

        try {
            // [1] 메세지 객체 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = getMimeMessageHelper(message);

            // [2] 이메일 제목 & 본문 생성 및 이메일 작성
            String title = "[STUDYON] 이메일 인증 요청입니다.";
            String content = createVerifyUrlContent(verifyUrl, expiration);
            setHelper(from, to, helper, title, content);

            // [3] 이메일 전송
            mailSender.send(message);

            // 이메일 전송 실패로 인한 체크 예외 발생 시
        } catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "인증 URL 이메일 전송 실패! 원인 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.UTILS_LOGIC_FAILED, e);
        }

    }

    @Override
    public void sendVerifyCode(String to, String verifyCode, Duration expiration) {

        try {
            // [1] 메세지 객체 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = getMimeMessageHelper(message);

            // [2] 이메일 제목 & 본문 생성 및 이메일 작성
            String title = "[STUDYON] 인증 코드 요청입니다.";
            String content = createVerifyCodeContent(verifyCode, expiration);
            setHelper(from, to, helper, title, content);

            // [3] 이메일 전송
            mailSender.send(message);

            // 이메일 전송 실패로 인한 체크 예외 발생 시
        } catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "인증 코드 이메일 전송 실패! 원인 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.UTILS_LOGIC_FAILED, e);
        }
    }


    // 파일을 첨부하기 위한 MimeMessageHelper 객체 생성 (파일 첨부 이메일)
    public MimeMessageHelper getMimeMessageHelper(MimeMessage message) throws MessagingException {
        return new MimeMessageHelper(message, true, "UTF-8");
    }


    // Helper 객체 설정 (이메일 수신자, 제목, 본문, 파일첨부)
    public void setHelper(String from, String to, MimeMessageHelper helper, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        helper.setFrom(from, FROM_USERNAME); // 발신자
        helper.setTo(to); // 수신자
        helper.setSubject(subject);
        helper.setText(content, true); // HTML 형식 허용
        if (IMAGE_LOGO.exists()) helper.addInline(CID_LOGO, IMAGE_LOGO); // 로고 이미지 삽입
    }


    // 인증 URL 메일 본문 HTML
    public String createVerifyUrlContent(String verifyUrl, Duration expiration) {

        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(expiration.toMinutes());

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta name="color-scheme" content="light dark">
            <meta name="supported-color-schemes" content="light dark">
        </head>
        <body style="margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Arial, sans-serif; background-color: #fafafa;">
            <table width="100%%" cellpadding="0" cellspacing="0" border="0" role="presentation" style="background-color: #fafafa; padding: 40px 20px;">
                <tr>
                    <td align="center">
                        <!-- 메인 컨테이너 -->
                        <table width="600" cellpadding="0" cellspacing="0" border="0" role="presentation" style="max-width: 600px; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
                            
                            <!-- 헤더 공간 -->
                            <tr>
                                <td style="padding: 40px 40px 30px 40px;">
                                    <table width="100%%" cellpadding="0" cellspacing="0" border="0">
                                        
                                        <!-- 로고 -->
                                        <tr>
                                            <td align="center" style="padding-bottom: 30px;">
                                                <img src="cid:%s" alt="STUDY ON" width="160" style="display: block; width: 160px; height: auto;" />
                                            </td>
                                        </tr>
                                        
                                        <!-- 제목 -->
                                        <tr>
                                            <td align="center" style="padding-bottom: 20px;">
                                                <h1 style="margin: 0; font-size: 24px; color: #1a1a1a; font-weight: 700; line-height: 1.3;">
                                                    이메일 인증 요청
                                                </h1>
                                            </td>
                                        </tr>
                                        
                                        <!-- 설명 -->
                                        <tr>
                                            <td align="center" style="padding-bottom: 30px;">
                                                <p style="margin: 0; font-size: 15px; color: #555555; line-height: 1.6;">
                                                    아래의 버튼을 클릭하여 이메일 인증을 완료해주세요.
                                                </p>
                                            </td>
                                        </tr>
                                        
                                        <!-- 버튼 -->
                                        <tr>
                                            <td align="center" style="padding: 10px 0 30px 0;">
                                                <!--[if mso]>
                                                <v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word" href="%s" style="height:48px;v-text-anchor:middle;width:200px;" arcsize="13%%" strokecolor="#4a6cf7" fillcolor="#4a6cf7">
                                                    <w:anchorlock/>
                                                    <center style="color:#ffffff;font-family:sans-serif;font-size:16px;font-weight:600;">이메일 인증하기</center>
                                                </v:roundrect>
                                                <![endif]-->
                                                <!--[if !mso]><!-->
                                                <a href="%s" 
                                                   target="_blank"
                                                   style="display: inline-block; 
                                                          padding: 14px 40px; 
                                                          background-color: #4a6cf7; 
                                                          color: #ffffff !important; 
                                                          text-decoration: none; 
                                                          font-size: 16px; 
                                                          font-weight: 600; 
                                                          border-radius: 6px;
                                                          mso-hide: all;">
                                                    이메일 인증하기
                                                </a>
                                                <!--<![endif]-->
                                            </td>
                                        </tr>
                                        
                                        <!-- 유효기간 -->
                                        <tr>
                                            <td align="center" style="padding-bottom: 20px;">
                                                <p style="margin: 0; font-size: 14px; color: #777777;">
                                                    인증 유효기간: <strong style="color: #333333;">%s</strong> 까지
                                                </p>
                                            </td>
                                        </tr>
                                        
                                    </table>
                                </td>
                            </tr>
                            
                            <!-- 백업 URL 영역 -->
                            <tr>
                                <td style="padding: 0 40px 40px 40px;">
                                    <table width="100%%" cellpadding="20" cellspacing="0" border="0" style="background-color: #f9f9f9; border-radius: 5px;">
                                        <tr>
                                            <td>
                                                <p style="margin: 0 0 10px 0; font-size: 13px; color: #666666; line-height: 1.5;">
                                                    만약 버튼이 작동하지 않으면 아래 URL을 복사하여 브라우저 주소창에 붙여넣어 주세요.
                                                </p>
                                                <p style="margin: 0; word-break: break-all; font-size: 12px; color: #4a6cf7; line-height: 1.5;">
                                                    %s
                                                </p>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            
                            <!-- 푸터 -->
                            <tr>
                                <td align="center" style="padding: 0 40px 40px 40px;">
                                    <p style="margin: 0; font-size: 12px; color: #999999; line-height: 1.5;">
                                        본인이 요청하지 않은 경우 이 메일을 무시해주세요.
                                    </p>
                                </td>
                            </tr>
                            
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
    """.formatted(CID_LOGO, verifyUrl, verifyUrl, expiresAt.format(TIME_FORMATTER), verifyUrl);
    }

    // 인증코드 메일 본문 HTML
    public String createVerifyCodeContent(String verifyCode, Duration expiration) {

        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(expiration.toMinutes());

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta name="color-scheme" content="light dark">
            <meta name="supported-color-schemes" content="light dark">
        </head>
        <body style="margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Arial, sans-serif; background-color: #fafafa;">
            <table width="100%%" cellpadding="0" cellspacing="0" border="0" role="presentation" style="background-color: #fafafa; padding: 40px 20px;">
                <tr>
                    <td align="center">
                        <!-- 메인 컨테이너 -->
                        <table width="600" cellpadding="0" cellspacing="0" border="0" role="presentation" style="max-width: 600px; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
                            <!-- 헤더 공간 -->
                            <tr>
                                <td style="padding: 40px 40px 30px 40px;">
                                    <table width="100%%" cellpadding="0" cellspacing="0" border="0">
                                        
                                        <!-- 로고 -->
                                        <tr>
                                            <td align="center" style="padding-bottom: 30px;">
                                                <img src="cid:%s" alt="STUDY ON" width="160" style="display: block; width: 160px; height: auto;" />
                                            </td>
                                        </tr>
                                        
                                        <!-- 제목 -->
                                        <tr>
                                            <td align="center" style="padding-bottom: 20px;">
                                                <h1 style="margin: 0; font-size: 24px; color: #1a1a1a; font-weight: 700; line-height: 1.3;">
                                                    이메일 인증 코드
                                                </h1>
                                            </td>
                                        </tr>
                                        
                                        <!-- 설명 -->
                                        <tr>
                                            <td align="center" style="padding-bottom: 30px;">
                                                <p style="margin: 0; font-size: 15px; color: #555555; line-height: 1.6;">
                                                    아래의 인증 코드를 입력하여 이메일 인증을 완료해주세요.
                                                </p>
                                            </td>
                                        </tr>
                                        
                                        <!-- 인증 코드 박스 -->
                                        <tr>
                                            <td align="center" style="padding: 10px 0 30px 0;">
                                                <table cellpadding="0" cellspacing="0" border="0" style="margin: 0 auto;">
                                                    <tr>
                                                        <td style="background-color: #f4f4f4; 
                                                                   padding: 20px 40px; 
                                                                   border-radius: 8px; 
                                                                   border: 2px dashed #d0d0d0;">
                                                            <p style="margin: 0; 
                                                                      font-size: 32px; 
                                                                      font-weight: bold; 
                                                                      letter-spacing: 8px; 
                                                                      color: #222222; 
                                                                      font-family: 'Courier New', monospace;
                                                                      text-align: center;">
                                                                %s
                                                            </p>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        
                                        <!-- 유효기간 -->
                                        <tr>
                                            <td align="center" style="padding-bottom: 20px;">
                                                <p style="margin: 0; font-size: 14px; color: #777777;">
                                                    인증 유효기간: <strong style="color: #333333;">%s</strong> 까지
                                                </p>
                                            </td>
                                        </tr>
                                        
                                        <!-- 안내 메시지 -->
                                        <tr>
                                            <td align="center">
                                                <table width="100%%" cellpadding="20" cellspacing="0" border="0" style="background-color: #fff8e1; border-radius: 5px; border-left: 4px solid #ffc107;">
                                                    <tr>
                                                        <td>
                                                            <p style="margin: 0; font-size: 13px; color: #666666; line-height: 1.5;">
                                                                💡 <strong>안내:</strong> 인증 코드는 한 번만 사용 가능하며, 
                                                                유효기간이 지나면 자동으로 만료됩니다.
                                                            </p>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        
                                    </table>
                                </td>
                            </tr>
                            
                            <!-- 푸터 -->
                            <tr>
                                <td align="center" style="padding: 0 40px 40px 40px;">
                                    <table width="100%%" cellpadding="0" cellspacing="0" border="0">
                                        <tr>
                                            <td align="center" style="padding-top: 20px; border-top: 1px solid #eeeeee;">
                                                <p style="margin: 0; font-size: 12px; color: #999999; line-height: 1.5;">
                                                    본인이 요청하지 않은 경우 이 메일을 무시해주세요.
                                                </p>
                                                <p style="margin: 10px 0 0 0; font-size: 12px; color: #cccccc;">
                                                    본 메일은 자동 발송되었습니다.
                                                </p>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
    """.formatted(CID_LOGO, verifyCode, expiresAt.format(TIME_FORMATTER));
    }




}
