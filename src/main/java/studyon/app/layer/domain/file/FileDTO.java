package studyon.app.layer.domain.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.FileType;
import studyon.app.common.enums.Entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * 파일 기본 정보 DTO
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class FileDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private Long fileId;
        private String originalName;
        private String storeName;
        private String ext;
        private Long size;
        private Entity entity;
        private FileType fileType;
        private String filePath; // 현재 파일이 저장된 경로 (도메인 제외)
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Upload implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        @JsonIgnore // JSON 직렬화 제외
        private transient MultipartFile file;

        private String originalName;
        private String storeName;
        private String ext;
        private Long size;
        private Long entityId; //  업로드 파일이 속하는 엔티티 고유번호
        private Entity entity; //  업로드 파일이 속하는 엔티티 타입
        private FileType fileType; // 파일 유형 (썸네일, 직접업로드, 프로필 이미지, 에디터, ...)
        private String filePath; // 현재 파일이 저장된 경로 (도메인 제외)
    }


}
