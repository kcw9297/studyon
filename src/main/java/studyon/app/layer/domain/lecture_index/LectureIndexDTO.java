package studyon.app.layer.domain.lecture_index;


import lombok.*;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture_video.LectureVideoDTO;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */


/**
 * 강의 목차 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LectureIndexDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long lectureIndexId;
        private String indexTitle;
        private Long indexNumber;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long lectureId;
        private LectureVideoDTO.Read lectureVideo;
        private String videoFileName;

    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private String indexTitle;
        private Long indexNumber;
        private Long lectureId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long lectureIndexId;
        private String indexTitle;
        private Long indexNumber;
        private Long lectureId;
    }
}
