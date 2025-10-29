package studyon.app.layer.domain.lecture;


import lombok.*;

import studyon.app.common.enums.*;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 강의 기본 정보를 담은 DTO
 * @version 1.0
 * @author khj00
 */


@NoArgsConstructor
public class LectureDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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
    @NoArgsConstructor
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
    @NoArgsConstructor
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
    public static class Search {

        // 사용자가 검색하는 값
        private String filter = "";
        private String keyword;
        private List<String> subjects;
        private List<String> subjectDetails;
        private List<String> difficulties;
        private String sort;

        // 서버 제공 값
        private Long memberId;
        private Subject subject; // 단일 조회 시

        // 객체 생성 시 사용자 옵션 초기화
        public Search() {
            this.filter = "";
            this.keyword = "";
            this.sort = "";
            this.subjects = new ArrayList<>();
            this.subjectDetails = new ArrayList<>();
            this.difficulties = new ArrayList<>();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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
        private SubjectDetail subjectDetail;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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
    @NoArgsConstructor
    public static class LectureVideoInfo {
        private String indexTitle;
        private Integer indexNumber;
        private String videoTitle;
        private String videoUrl;
        private Integer seq;
        private Integer duration;
    }
}
