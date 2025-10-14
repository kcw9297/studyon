package studyon.app.layer.domain.lecture;


import lombok.*;
import lombok.experimental.SuperBuilder;

import studyon.app.common.enums.Difficulty;
import studyon.app.infra.aop.LogInfo;

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

        private Double price;
        private Difficulty difficulty;
        private Long videoCount;
        private Long totalStudents;
        private Double averageRate;
        private Long likeCount;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {

        private String title;
        private String description;
        private Double price;
        private Difficulty difficulty;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long lectureId;

        private String title;
        private String description;
        private Double price;
        private Difficulty difficulty;
    }
}
