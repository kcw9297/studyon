package studyon.app.layer.domain.category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture_category.repository.LectureCategoryRepository;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.List;
import java.util.stream.Collectors;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 카테고리 서비스 구현체
 * @version 1.0
 * @author khj00
 */

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final LectureCategoryRepository lectureCategoryRepository;

    @Override
    public List<LectureDTO.Read> getLecturesByCategoryId(Long categoryId) {
        return lectureCategoryRepository.findLecturesByCategoryId(categoryId)
                .stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO.Read> getTeachersByCategoryId(Long categoryId) {
        return lectureCategoryRepository.findTeachersByCategoryId(categoryId)
                .stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }
}
