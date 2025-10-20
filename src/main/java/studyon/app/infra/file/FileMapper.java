package studyon.app.infra.file;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Entity;
import studyon.app.layer.domain.file.FileDTO;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileMapper {

    static FileDTO.Upload toUploadDTO(MultipartFile file, Long entityId, Entity entity) {

        // [1] 파일 정보 추출
        String originalName = file.getOriginalFilename();
        String ext = Objects.isNull(originalName) || originalName.isBlank() ?
                "" : originalName.substring(originalName.lastIndexOf(".") + 1);
        String storeName = "%s.%s".formatted(UUID.randomUUID().toString(), ext);

        // [2] 추출 정보 기반 DTO 생성 및 반환
        return FileDTO.Upload.builder()
                .originalName(originalName)
                .storeName(storeName)
                .ext(ext)
                .size(file.getSize())
                .entityId(entityId)
                .entity(entity)
                .build();
    }
}
