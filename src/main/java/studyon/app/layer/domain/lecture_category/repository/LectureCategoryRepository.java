package studyon.app.layer.domain.lecture_category.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture_category.LectureCategory;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의-카테고리 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureCategoryRepository extends JpaRepository<LectureCategory, Long> {
    // 카테고리별 강의 조회
    @Query("SELECT lc.lecture FROM LectureCategory lc WHERE lc.category.categoryId = :categoryId")
    List<Lecture> findLecturesByCategoryId(@Param("categoryId") Long categoryId);

    // 카테고리별 선생님 조회 (중복 제거)
    @Query("SELECT DISTINCT l.teacher FROM LectureCategory lc JOIN lc.lecture l WHERE lc.category.categoryId = :categoryId")
    List<Teacher> findTeachersByCategoryId(@Param("categoryId") Long categoryId);
}
