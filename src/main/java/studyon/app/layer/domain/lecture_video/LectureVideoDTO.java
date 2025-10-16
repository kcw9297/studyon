package studyon.app.layer.domain.lecture_video;

import lombok.*;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * 강의 영상 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LectureVideoDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long lectureVideoId;
        private String title;
        private Integer seq;            // 영상 순서 (1, 2, 3, ...)
        private Integer duration;       // 영상 길이 (초 단위)
        private String videoUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long lectureIndexId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private String title;
        private Integer seq;
        private Integer duration;
        private String videoUrl;
        private Long lectureIndexId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long lectureVideoId;
        private String title;
        private Integer seq;
        private Integer duration;
        private String videoUrl;
        private Long lectureIndexId;
    }
}
