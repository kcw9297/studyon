package studyon.app.infra.aws;

import jakarta.servlet.http.HttpServletResponse;

public interface AWSCloudFrontProvider {

    /**
     * CloudFront Signed Cookie 설정
     * @param response HttpServletResponse (응답에 헤더를 담음)
     */

    void setSignedCookies(HttpServletResponse response);

    /**
     * CloudFront Signed URL 발급
     * @param fileUrl  CloudFront에서 접근할 파일 경로 (예: videos/sample.mp4)
     * @return 서명된 URL (https://{cloudFrontDomain}/{fileUrl}?Expires=...&Signature=...&Key-Pair-Id=...)
     */
    String createSignedUrl(String fileUrl);
}
