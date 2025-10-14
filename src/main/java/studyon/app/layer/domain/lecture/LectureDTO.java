package studyon.app.layer.domain.lecture;


import lombok.*;
import lombok.experimental.SuperBuilder;

import studyon.app.common.enums.Difficulty;
import studyon.app.infra.aop.LogInfo;

/**
 * DTO 클래스
 * @version 1.0
 * @author khj00
 */


@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LectureDTO {
    @Data
    @SuperBuilder
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read extends LogInfo {
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
    @ToString
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
    @ToString
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
