package studyon.app.layer.domain.lecture;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import studyon.app.common.enums.filter.LectureSort;
import studyon.app.common.enums.*;
import studyon.app.layer.base.validation.annotation.*;
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
    public static class Search {

        // 사용자가 검색하는 값
        private String filter = "";
        private String keyword;
        private List<String> subjects;
        private List<String> subjectDetails;
        private List<String> difficulties;
        private List<String> targets;
        private String sort;
        private Long minPrice;
        private Long maxPrice;

        // 관리자 검색 시 사용하는 값
        private List<String> onSales;
        private List<String> statuses;

        // 서버 제공 값
        private Long memberId;
        private Subject subject; // 단일 조회 시
        private String role; // 검색을 요청하는 회원 상태

        // 객체 생성 시 사용자 옵션 초기화
        public Search() {
            this.filter = "";
            this.keyword = "";
            this.sort = "";
            this.minPrice = 0L;
            this.maxPrice = 0L;
            this.sort = LectureSort.LATEST.name();
            this.subjects = new ArrayList<>();
            this.subjectDetails = new ArrayList<>();
            this.difficulties = new ArrayList<>();
            this.targets = new ArrayList<>();
            this.onSales = new ArrayList<>();
            this.statuses = new ArrayList<>();
        }

        // 검색을 요청하는 대상 설정
        public void setSearchTarget(Long memberId, Role role) {
            this.memberId = memberId;
            this.role = role.name();
        }
    }



    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Read {

        private String thumbnailImagePath;
        private Long lectureId;
        private String title;
        private String summary;
        private String description;
        private Long price;
        private Long teacherId;
        private String teacherNickname;
        private Difficulty difficulty;
        private Long videoCount;
        private Long totalStudents;
        private Long totalDuration;
        private Double averageRate;
        private Long likeCount;
        private LocalDateTime publishDate;
        private LectureTarget lectureTarget;
        private Subject subject;
        private SubjectDetail subjectDetail;
        private List<LectureIndexDTO.Read> lectureIndexes;

        // 관리자 검색 시 추가 제공 정보
        private Boolean onSale;
        private LectureRegisterStatus lectureRegisterStatus;
        private LectureDTO.Read lecture;
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
    @NoArgsConstructor
    public static class Create {

        private String editorId; // 에디터 작성 데이터를 기록하기 위해 임시 사용

        private Long teacherId;

        @Title(min = 4, max = 20)
        private String title;

        @Text(min = 10, max = 100)
        private String summary;

        @EditorContent(min = 10, max = 100)
        private String description;

        @NotNull(message = "강의 대상을 선택해야 합니다.")
        private LectureTarget target;

        @NotNull(message = "강의 난이도를 선택해야 합니다.")
        private Difficulty difficulty;

        @NotNull(message = "강의 세부 과목을 선택해야 합니다.")
        private SubjectDetail subjectDetail;

        @LongRange(min = 10000, max = 1000000)
        private Long price;

        @LectureIndex
        private List<String> curriculumTitles;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadLectureInfo {
        private Long teacherId;
        private String title;
        private String summary;
        private String description;
        private LectureTarget target;
        private Long price;
        private Difficulty difficulty;
        private Subject subject;
        private SubjectDetail subjectDetail;
        private List<LectureVideoInfo> videos;
        private String teacherName;
        private LectureRegisterStatus lectureRegisterStatus;
        private String thumbnailImagePath;
        private Boolean onSales;
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

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Simple {
        private Long lectureId;
        private String title;
        private String registerStatus;
    }
}
