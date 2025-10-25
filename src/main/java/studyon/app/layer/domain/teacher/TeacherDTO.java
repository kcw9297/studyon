package studyon.app.layer.domain.teacher;

import lombok.*;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture.Lecture;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : khj00 최초 작성
 */

/**
 * 선생님 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor
public class TeacherDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long teacherId;

        private Long memberId;
//        private String email;
        private String description;
        private Subject subject;
        private Long totalStudents;
        private Long totalReview;
        private Double averageRating;

        private String nickname;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private String description;
        private Subject subject;
        private String nickname;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long teacherId;

        private String description;
        private Subject subject;

        private String nickname;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Search {
        private Long teacherId;

        // private String filter; // "subject"
        private String keyword;
        private Subject subject;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class LectureListResponse {
        private Long teacherId;
        private String nickname;
        private List<LectureSimple> pending;
        private List<LectureSimple> registered;
        private List<LectureSimple> unregistered;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor(access = AccessLevel.PACKAGE)
        public static class LectureSimple {
            private Long lectureId;
            private String title;
            private String status;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class TeacherManagementProfile{
        private Long teacherId;
        private Long memberId;
        private String nickname;
        private String email;
        private String description;
        private Subject subject;
        private String profileImageUrl;
        private Long lectureCount;
        private Long totalStudent;
        private Double averageRating;
    }
}
