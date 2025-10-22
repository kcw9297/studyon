package studyon.app.layer.domain.file;

import lombok.*;
import lombok.experimental.SuperBuilder;
import studyon.app.common.enums.FileType;
import studyon.app.infra.aop.LogInfo;
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
    @SuperBuilder
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read extends LogInfo {

        private Long fileId;
        private String originalName;
        private String storeName;
        private String ext;
        private Long size;
        private Entity entity;
        private FileType fileType;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Upload implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private String originalName;
        private String storeName;
        private String ext;
        private Long size;
        private Long entityId;
        private Entity entity;
        private FileType fileType;
        private String filePath;
    }


}
