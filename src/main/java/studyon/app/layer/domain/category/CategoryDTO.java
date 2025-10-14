package studyon.app.layer.domain.category;

import lombok.*;



/**
 * 카테고리 기본 정보 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CategoryDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long categoryId;
        private String name;

        private Long parentId; // 부모 카테고리 ID만 전달함
        private Long lectureId; // 연결된 강의 ID
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private String name;
        private Long parentId;
        private Long lectureId;
    }
}
