package studyon.app.layer.domain.lecture_category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.category.Category;
import studyon.app.layer.domain.category.CategoryDTO;
import studyon.app.layer.domain.category.repository.CategoryRepository;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_category.LectureCategory;
import studyon.app.layer.domain.lecture_category.LectureCategoryDTO;
import studyon.app.layer.domain.lecture_category.repository.LectureCategoryRepository;

import java.util.stream.Collectors;

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

    /**
     * 연관 카테고리 매핑
     * @param lectureId 강의 아이디
     * @param categoryId 카테고리 아이디
     * @return PaymentDetails(Optional)
     */
    @Override
    public LectureCategoryDTO.Read saveMapping(Long lectureId, Long categoryId) {
        // [1] 각각 조회하는 단계
        LectureDTO.Read lecture = lectureRepository.findById(lectureId)
                .map(DTOMapper::toReadDTO)
                .orElseThrow();
        CategoryDTO.Read category = categoryRepository.findById(categoryId)
                .map(DTOMapper::toReadDTO)
                .orElseThrow();
        // [2] DTO 저장 후 리턴
        return LectureCategoryDTO.Read.builder()
                .lectureId(lecture.getLectureId())
                .categoryId(category.getCategoryId())
                .build();
    }
}
