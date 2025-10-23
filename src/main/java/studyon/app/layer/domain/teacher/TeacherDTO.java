package studyon.app.layer.domain.teacher;

import lombok.*;
import studyon.app.common.enums.Subject;

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
}
