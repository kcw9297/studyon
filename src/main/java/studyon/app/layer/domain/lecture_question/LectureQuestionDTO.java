package studyon.app.layer.domain.lecture_question;


import lombok.*;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : khj00 최초 작성
 */

/**
 * 강의 질문 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor
public class LectureQuestionDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long lectureQuestionId;
        private Long lectureId;
        private String title;
        private String content;
        private Long answerCount;
        private Long viewCount;
        private Boolean isSolved;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private Long lectureId;
        private String title;
        private String content;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long lectureQuestionId;
        private Long lectureId;
        private String title;
        private String content;
    }
}
