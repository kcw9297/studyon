package studyon.app.layer.domain.log;

import lombok.*;
import studyon.app.common.enums.Action;
import studyon.app.common.enums.Entity;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LogDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {

        private String email;
        private String ipAddress;
        private Long entityId;
        private Entity entity;
        private Action action;
        private Boolean isSuccess;
        private LocalDateTime actionAt;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Generate {

        private String email;
        private String ipAddress;
        private Long entityId;
        private Entity entity;
        private Action action;
        private Boolean isSuccess;
    }


}
