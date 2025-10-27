package studyon.app.layer.domain.notice;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import studyon.app.common.enums.NoticeType;
import studyon.app.common.enums.Provider;
import studyon.app.infra.aop.LogInfo;
import studyon.app.layer.domain.file.FileDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public static class Search {

        private String filter;
        private String keyword;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {

        private Long noticeId;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private LocalDateTime cdate;
        private String title;
        private String content;
        private NoticeType noticeType;
        private Boolean isActivate;
        private FileDTO.Read noticeImage;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {

        private LocalDate startedAt;

        private LocalDate endedAt;

        private String title;

        private String content;

        private NoticeType noticeType;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {

        private Long noticeId;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private String title;
        private String content;
        private NoticeType noticeType;
    }

}
