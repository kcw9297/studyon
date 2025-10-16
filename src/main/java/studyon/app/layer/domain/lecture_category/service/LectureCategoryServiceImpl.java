package studyon.app.layer.domain.lecture_category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studyon.app.layer.domain.category.Category;
import studyon.app.layer.domain.category.repository.CategoryRepository;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_category.LectureCategory;
import studyon.app.layer.domain.lecture_category.repository.LectureCategoryRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureCategoryServiceImpl implements LectureCategoryService {

    private final LectureRepository lectureRepository;
    private final CategoryRepository categoryRepository;
    private final LectureCategoryRepository lectureCategoryRepository;


    @Override
    public LectureCategory saveMapping(Long lectureId, Long categoryId) {
        // 각각 조회하는 단계
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의 ID: " + lectureId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 ID: " + categoryId));

        LectureCategory mapping = LectureCategory.builder()
                .lecture(lecture)
                .category(category)
                .build();

        return lectureCategoryRepository.save(mapping);
    }
}
