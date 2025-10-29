package studyon.app.layer.domain.lecture_category;


import lombok.*;

@NoArgsConstructor
public class LectureCategoryDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Read {
        private Long lectureCategoryId;
        private Long lectureId;
        private Long categoryId;
    }
}
