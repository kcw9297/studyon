package studyon.app.layer.domain.log;

import lombok.*;
import studyon.app.common.enums.Action;
import studyon.app.common.enums.Entity;

import java.time.LocalDateTime;

/**
 * 로그 DTO
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor
public class LogDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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
    @NoArgsConstructor
    public static class Generate {

        private String email;
        private String ipAddress;
        private Long entityId;
        private Entity entity;
        private Action action;
        private Boolean isSuccess;
    }


}
