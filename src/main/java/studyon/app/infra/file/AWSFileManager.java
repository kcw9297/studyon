package studyon.app.infra.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import studyon.app.common.constant.AppProfile;
import studyon.app.common.enums.Entity;
import studyon.app.common.exception.ManagerException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.file.FileDTO;

/**
 * AWS S3 스토리지 조작 메소드 처리 클래스
 * @version 1.0
 * @author kcw97
 */

@Profile(AppProfile.PROD)
@Component
@RequiredArgsConstructor
public class AWSFileManager implements FileManager {

    private final S3Client s3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    @Override
    public FileDTO.Upload upload(MultipartFile file, Long entityId, Entity entity) {

        try {

            // [1] 업로드 파일 DTO 생성 및 저장 경로 생성
            FileDTO.Upload dto = DTOMapper.toUploadDTO(file, entityId, entity);

            String key = // 저장 경로 (key : 저장 경로 + 파일명)
                    "%s/%s".formatted(entity.getValue(), dto.getStoreName());

            // [2] S3 업로드 요청 객체 생성
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            // [3] S3 업로드
            s3.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return dto;

        } catch (Exception e) {
            throw new ManagerException("AWS S3 파일 업로드에 실패했습니다!", e);
        }
    }


    @Override
    public byte[] download(String storeName, Entity entity) {

        try {
            // [1] 파일 경로 (key : 저장 경로 + 파일명)
            String key = "%s/%s".formatted(entity.getValue(), storeName);

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
            throw new ManagerException("AWS S3 파일 다운로드에 실패했습니다!", e);
        }
    }


    @Override
    public void remove(String storeName, Entity entity) {

        // [1] 파일 경로 (key : 저장 경로 + 파일명)
        String key = "%s/%s".formatted(entity.getValue(), storeName);

        // [2] 파일 삭제
        s3.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
    }
}
