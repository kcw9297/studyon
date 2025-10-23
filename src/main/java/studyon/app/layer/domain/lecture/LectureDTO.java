package studyon.app.layer.domain.lecture;


import lombok.*;

import studyon.app.common.enums.Difficulty;
import studyon.app.common.enums.Subject;
import studyon.app.infra.aop.LogInfo;

import java.time.LocalDateTime;

/**
 * 강의 기본 정보를 담은 DTO
 * @version 1.0
 * @author khj00
 */


@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LectureDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long lectureId;

        private String title;
        private String description;

        private Long teacherId;

        private Double price;
        private Difficulty difficulty;
        private Long videoCount;
        private Long totalStudents;
        private Long totalDuration;
        private Double averageRate;
        private Long likeCount;
        private Boolean onSale;
        private LocalDateTime publishDate;

        private String nickname;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private Long teacherId;
        private String title;
        private String description;
        private Double price;
        private Difficulty difficulty;
        private Boolean onSale;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long lectureId;
        private Long teacherId;
        private String title;
        private String description;
        private Double price;
        private Difficulty difficulty;
        private Boolean onSale;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Search {
        private String filter; // "title", "difficulty", "controller"
        private String keyword;
        private Subject subject;
    }
}
