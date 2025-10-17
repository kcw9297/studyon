package studyon.app.layer.domain.lecture_category.service;

import studyon.app.layer.domain.lecture_category.LectureCategoryDTO;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureCategoryService {
    /**
     * 강의-카테고리 매핑 생성 후 저장
     * @param lectureId 강의 ID
     * @param categoryId 카테고리 ID
     * @return 저장된 LectureCategory 엔티티
     */
    LectureCategoryDTO.Read saveMapping(Long lectureId, Long categoryId);
}
