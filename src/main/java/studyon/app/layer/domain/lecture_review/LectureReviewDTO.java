package studyon.app.layer.domain.lecture_review;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * 강의 리뷰 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LectureReviewDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long lectureReviewId;
        private String content;
        private Integer rating;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long lectureId;
        private Long memberId;

        private String nickname;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private String content;
        @Min(1)
        @Max(5)
        private Integer rating;
        private Long lectureId;
        private Long memberId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long lectureReviewId;
        private String content;
        @Min(1)
        @Max(5)
        private Integer rating;
    }
}
