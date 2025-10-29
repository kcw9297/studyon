package studyon.app.layer.domain.lecture_question;


import lombok.*;

import java.time.LocalDateTime;

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
    @NoArgsConstructor
    public static class Read {
        private Long lectureQuestionId;
        private String title;
        private String content;
        private Boolean isSolved;
        private Long answerCount;
        private Long viewCount;
        private Long lectureId;
        private Long memberId;
        private String memberNickname;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Write {
        private Long lectureQuestionId;
        private Long lectureId;
        private String title;
        private String content;
        private Long memberId;
        private String memberNickname;
        private LocalDateTime createdAt;
        private Long lectureIndexId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Edit {
        private Long lectureQuestionId;
        private Long lectureId;
        private String title;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadQna {

        // 질문 정보
        private Long questionId;
        private String title;
        private String content;
        private boolean isSolved;
        private LocalDateTime questionCreatedAt;
        private String answerContent;
        private LocalDateTime answerCreatedAt;
        private Long lectureId;
        private Long lectureIndexId;
        private String indexTitle;
    }

}
