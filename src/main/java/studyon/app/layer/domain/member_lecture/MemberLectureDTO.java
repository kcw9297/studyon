package studyon.app.layer.domain.member_lecture;

import lombok.*;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.member.MemberDTO;

import java.time.LocalDateTime;

/**
 * 회원별 강의 DTO
 * @version 1.0
 * @author 김효상
 */
@NoArgsConstructor
public class MemberLectureDTO {

    /**
     * ✅ 조회용 DTO (Read)
     * - MyPage 강의관리 화면에서 사용
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Read {
        private Long memberLectureId;
        private Long lectureId;
        private String lectureTitle;
        private String teacherName;
        private String subject;
        private Double progress;
        private Boolean completed;
    }


    /**
     * ✅ 등록용 DTO (Write)
     * - 강의 구매 시, member_lecture에 새 수강 정보 추가
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Write {
        private Long memberId;
        private Long lectureId;
        private int progress; // 기본값 0
        private boolean completed; // 기본값 false
    }

    /**
     * ✅ 수정용 DTO (Edit)
     * - 진도율 업데이트 시 사용
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Edit {
        private Long memberLectureId;
        private int progress;
        private boolean completed;
        private LocalDateTime updatedAt;
    }
}
