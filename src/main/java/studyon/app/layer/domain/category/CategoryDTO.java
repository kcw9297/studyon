package studyon.app.layer.domain.category;

import lombok.*;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

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

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Edit {
        private Long categoryId;  // 수정할 카테고리 식별자
        private String name;      // 수정할 이름
        private Long parentId;    // 부모 카테고리가 변경될 수도 있음
        private Long lectureId;   // 강의 연결이 변경될 수도 있음
    }

}
