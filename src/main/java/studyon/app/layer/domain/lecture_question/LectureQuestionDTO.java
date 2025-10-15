package studyon.app.layer.domain.lecture_question;


import lombok.*;

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
        private Long lectureQnaId;

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
        private String title;
        private String content;
    }
}
