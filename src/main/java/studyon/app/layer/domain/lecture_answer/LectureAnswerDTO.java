package studyon.app.layer.domain.lecture_answer;


import lombok.*;
import studyon.app.layer.domain.lecture_question.LectureQuestionDTO;
import studyon.app.layer.domain.member.MemberDTO;

import java.time.LocalDateTime;

/**
 * 강의 답변 관련 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor
public class LectureAnswerDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Read {
        private Long lectureAnswerId;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long lectureQuestionId;
        // private LectureQuestionDTO.Read lectureQuestionId;
        private Long parentId;
        private Long memberId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Write {
        private String content;
        private Long lectureQuestionId;
        private Long parentId;            // 부모 답변 (대댓글인 경우)
        private Long memberId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Edit {
        private Long lectureAnswerId;
        private String content;
        private Long memberId;           // 수정 요청자 (작성자 본인 검증용)
    }
}
