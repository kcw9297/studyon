package studyon.app.layer.domain.teacher;

import lombok.*;
import lombok.experimental.SuperBuilder;
import studyon.app.infra.aop.LogInfo;

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

        private String description;
        private String subject;
        private Long totalStudents;
        private Long totalReview;
        private Double averageRating;

    }

}
