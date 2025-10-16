package studyon.app.layer.domain.lecture_category.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.layer.domain.category.repository.CategoryRepository;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LectureCategoryRepositoryTest {
    @Autowired
    private LectureCategoryRepository lectureCategoryRepository;

    @Test
    @DisplayName("강의-카테고리 매핑 테이블 저장 및 조회 테스트")
    void saveLectureCategoryMapping() {

    }
}