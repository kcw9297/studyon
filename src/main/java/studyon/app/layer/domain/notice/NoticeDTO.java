package studyon.app.layer.domain.notice;

import lombok.*;
import studyon.app.layer.domain.file.FileDTO;

/**
 * 공지 정보를 담은 DTO
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class NoticeDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {

        private Integer idx;
        private String title;
        private Boolean isActivate;
        private FileDTO.Read noticeImage;
    }

}
