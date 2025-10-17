package studyon.app.layer.domain.category.service;

import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 카테고리 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface CategoryService {
    // 강의 카테고리별 강의 조회
    List<LectureDTO.Read> getLecturesByCategoryId(Long categoryId);
    // 강의 카테고리별 선생님 조회
    List<TeacherDTO.Read> getTeachersByCategoryId(Long categoryId);
}
