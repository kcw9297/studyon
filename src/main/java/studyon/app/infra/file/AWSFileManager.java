package studyon.app.infra.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import studyon.app.common.constant.Env;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.ManagerException;
import studyon.app.common.utils.StrUtils;



/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 예외 처리방식 변경
 */

/**
 * AWS S3 스토리지 조작 메소드 처리 클래스
 * @version 1.1
 * @author kcw97
 */

@Profile(Env.PROFILE_PROD)
@Slf4j
@Component
@RequiredArgsConstructor
public class AWSFileManager implements FileManager {

    private final S3Client s3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.cloudfront.domain}")
    private String cloudFrontDomain;


    @Override
    public String upload(MultipartFile file, String storeName, String entityName) {

        try {

            // [1] 저장 경로 생성
            String key = // 저장 경로 (key : 저장 경로 + 파일명)
                    "%s/%s".formatted(entityName, entityName);

            // [2] S3 업로드 요청 객체 생성
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            // [3] S3 업로드 후 저장 경로 반환
            s3.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return "%s/%s".formatted(cloudFrontDomain, key);

        } catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "AWS S3 파일 업로드에 실패! 원인 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.SERVER_ERROR, e);
        }
    }



    @Override
    public byte[] download(String storeName, String entityName) {

        try {
            // [1] 파일 경로 (key : 저장 경로 + 파일명)
            String key = "%s/%s".formatted(entityName, storeName);

            // [2] S3 스토리지에 저장된 파일 다운로드 요청 (InputStream)
            ResponseInputStream<GetObjectResponse> getRes = s3.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build()
            );

            // [3] 파일 다운로드 수행 후, byte 문자열 반환
            return getRes.readAllBytes();

        } catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "AWS S3 파일 다운로드 실패! 원인 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.SERVER_ERROR, e);
        }
    }


    @Override
    public void remove(String storeName, String entityName) {

        // [1] 파일 경로 (key : 저장 경로 + 파일명)
        String key = "%s/%s".formatted(entityName, storeName);

        // [2] 파일 삭제
        s3.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
    }

    @Override
    public void remove(String filePath) {
        s3.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(filePath).build());
    }

}
