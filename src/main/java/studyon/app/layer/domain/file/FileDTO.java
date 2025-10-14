package studyon.app.layer.domain.file;

import lombok.*;
import lombok.experimental.SuperBuilder;
import studyon.app.infra.aop.LogInfo;
import studyon.app.common.enums.Entity;

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
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Upload {

        private String originalName;
        private String storeName;
        private String ext;
        private Long size;
        private Long entityId;
        private Entity entity;
    }


}
