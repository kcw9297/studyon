package studyon.app.layer.domain.lecture;


import lombok.*;

import studyon.app.common.enums.Difficulty;
import studyon.app.common.enums.LectureRegisterStatus;
import studyon.app.common.enums.LectureTarget;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;

import java.time.LocalDateTime;
import java.util.List;

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

        private LectureRegisterStatus lectureRegisterStatus;

        private Long price;
        private Difficulty difficulty;
        private Long videoCount;
        private Long totalStudents;
        private Long totalDuration;
        private Double averageRate;
        private Long likeCount;
        private Boolean onSale;
        private LocalDateTime publishDate;

        private String nickname;

        private List<LectureIndexDTO.Read> lectureIndexes;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private Long teacherId;
        private String title;
        private String description;
        private Long price;
        private LectureRegisterStatus lectureRegisterStatus;
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
        private Long price;
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

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Register {
        private Long teacherId;
        private String title;
        private String description;
        private LectureTarget target;
        private Long price;
        private Difficulty difficulty;
        private LectureRegisterStatus lectureRegisterStatus;
        private Subject subject;
        private List<String> curriculumTitles;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class ReadLectureInfo {
        private Long teacherId;
        private String title;
        private String description;
        private LectureTarget target;
        private Long price;
        private Difficulty difficulty;
        private Subject subject;
        private List<LectureVideoInfo> videos;
        private String teacherName;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class LectureVideoInfo {
        private String indexTitle;
        private Integer indexNumber;
        private String videoTitle;
        private String videoUrl;
        private Integer seq;
        private Integer duration;
    }
}
