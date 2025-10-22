package studyon.app.infra.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.Env;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.exception.ManagerException;
import studyon.app.layer.domain.file.FileDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-20) : kcw97 임시파일 저장/삭제 가능 추가. FileType 추가
 */

/**
 * 파일의 저장, 삭제, 다운로드 메소드 처리
 * @version 1.1
 * @author kcw97
 */

@Profile(Env.PROFILE_LOCAL)
@Component
public class LocalFileManager implements FileManager {

    @Value("${file.dir}") // properties(yml) 파일 내 프로퍼티 값을 직접 사용 (빈 주입 시기에 같이 삽입)
    private String fileDir;


    @Override
    public FileDTO.Upload upload(MultipartFile file, Long entityId, Entity entity, FileType fileType) {

        try {
            // [1] 저장 파일 디렉토리 확인 (없을 시 생성)
            String uploadPath = "%s/%s/".formatted(fileDir, entity.getName());
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            // [2] 파일 검증
            if (Objects.isNull(file) || file.isEmpty())
                throw new ManagerException("저장할 파일이 존재하지 않습니다!"); // 파일이 존재하지 않으면 예외 발생

            // [3] 파일 업로드 & 파일 업로드 정보 DTO 생성 후 반환
            return uploadAndMapToDto(file, entityId, entity, fileType, uploadPath);


        } catch (ManagerException e) {
            throw e;

        } catch (Exception e) {
            throw new ManagerException("로컬 스토리지 파일 업로드에 실패했습니다!", e);
        }
    }

    // 업로드 후, 파일 정보 DTO 생성
    private FileDTO.Upload uploadAndMapToDto(MultipartFile file, Long entityId, Entity entity, FileType fileType, String uploadPath) throws IOException {
        String originalName = file.getOriginalFilename();
        String ext = Objects.isNull(originalName) || originalName.isBlank() ?
                "" : originalName.substring(originalName.lastIndexOf(".") + 1);
        String storeName = "%s.%s".formatted(UUID.randomUUID().toString(), ext);
        String filePath = "%s/%s".formatted(uploadPath, storeName);


        // [4] 파일 업로드
        file.transferTo(new File(filePath)); // 파일 업로드

        // [5] 업로드 정보 DTO 생성 및 반환
        return FileDTO.Upload.builder()
                .originalName(originalName)
                .storeName(storeName)
                .ext(ext)
                .size(file.getSize())
                .entityId(entityId)
                .entity(entity)
                .fileType(fileType)
                .filePath(filePath)
                .build();
    }


    @Override
    public byte[] download(String storeName, Entity entity) {

        try {
            // [1] 파일 저장 경로
            String filePath = "%s/%s/%s".formatted(fileDir, entity.getName(), storeName);

            // [2] Path 생성
            Path path = Paths.get(filePath);

            // [3] 파일 바이트 문자열 반환
            return Files.readAllBytes(path);

        } catch (Exception e) {
            throw new ManagerException("로컬 스토리지 파일 다운로드에 실패했습니다!", e);
        }
    }


    @Override
    public void remove(String storeName, Entity entity) {

        try {
            // [1] 파일 저장 경로
            String filePath = "%s/%s/%s".formatted(fileDir, entity.getName(), storeName);

            // [2] 파일 삭제
            Files.delete(Paths.get(filePath));

        } catch (Exception e) {
            throw new ManagerException("로컬 스토리지 파일 삭제에 실패했습니다!", e);
        }
    }


}
