package studyon.app.layer.domain.lecture_category.service;

import studyon.app.layer.domain.lecture_category.LectureCategory;

public interface LectureCategoryService {
    /**
     * 강의-카테고리 매핑 생성 후 저장
     * @param lectureId 강의 ID
     * @param categoryId 카테고리 ID
     * @return 저장된 LectureCategory 엔티티
     */
    LectureCategory saveMapping(Long lectureId, Long categoryId);
}
