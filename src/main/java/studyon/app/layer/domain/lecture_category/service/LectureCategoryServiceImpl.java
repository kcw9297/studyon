package studyon.app.layer.domain.lecture_category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.category.Category;
import studyon.app.layer.domain.category.repository.CategoryRepository;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_category.LectureCategory;
import studyon.app.layer.domain.lecture_category.LectureCategoryDTO;
import studyon.app.layer.domain.lecture_category.repository.LectureCategoryRepository;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의-카테고리 서비스 구현체
 * @version 1.0
 * @author khj00
 */

@Service
@RequiredArgsConstructor
@Transactional
public class LectureCategoryServiceImpl implements LectureCategoryService {

    private final LectureRepository lectureRepository;
    private final CategoryRepository categoryRepository;
    private final LectureCategoryRepository lectureCategoryRepository;


    @Override
    public LectureCategoryDTO.Read saveMapping(Long lectureId, Long categoryId) {
        // 각각 조회하는 단계
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의 ID: " + lectureId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 ID: " + categoryId));

        LectureCategory mapping = LectureCategory.builder()
                .lecture(lecture)
                .category(category)
                .build();

        LectureCategory saved = lectureCategoryRepository.save(mapping);
        return DTOMapper.toReadDTO(saved);
    }
}
