package studyon.app.layer.domain.lecture_category;


import lombok.*;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LectureCategoryDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long lectureCategoryId;
        private Long lectureId;
        private Long categoryId;
    }
}
