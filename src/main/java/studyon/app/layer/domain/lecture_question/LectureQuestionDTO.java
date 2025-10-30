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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadTeacherQnaDTO {

        private Long lectureQuestionId;   // 질문 ID
        private String title;             // 질문 제목
        private String content;           // 질문 내용
        private String studentName;       // 질문 작성자 (익명처리 가능)
        private String indexTitle;        // 목차명 (예: "3강 - 오리엔테이션")
        private boolean answered;         // 답변 여부
        private LocalDateTime createdAt;  // 질문 등록일
        private LocalDateTime answeredAt; // 답변일 (없으면 null)
        private Long lectureIndexId;

        // UI 표시용 필드 (optional)
        public String getStatusLabel() {
            return answered ? "답변 완료" : "미답변";
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeacherQnaDetail {
        private Long lectureQuestionId;
        private String title;
        private String content;
        private String studentName;
        private String indexTitle;
        private String lectureTitle;
        private Long lectureId;
        private String teacherName;
        private String answerContent;
        private LocalDateTime createdAt;
        private LocalDateTime answeredAt;
    }

}
