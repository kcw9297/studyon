package studyon.app.layer.domain.lecture_category.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.common.enums.Difficulty;
import studyon.app.layer.domain.category.Category;
import studyon.app.layer.domain.category.repository.CategoryRepository;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_category.LectureCategory;
import studyon.app.layer.domain.lecture_category.repository.LectureCategoryRepository;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LectureCategoryServiceTest {

    @Autowired
    private LectureCategoryService lectureCategoryService;

    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @DisplayName("강의 - 카테고리 매핑 생성 및 저장 테스트")
    void saveLectureMapping() {
        // given
        Teacher teacher = teacherRepository.findById(2L)
                .orElseThrow(() -> new IllegalArgumentException("이런 선생님은 없어요..."));

        Lecture lecture = lectureRepository.save(
                Lecture.builder()
                        .title("테스트 강의")
                        .price(0.0)
                        .teacher(teacher)
                        .description("테스트")
                        .difficulty(Difficulty.BASIC)
                        .build()
        );

        Category category = categoryRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("강의 카테고리 오류"));

        LectureCategory savedMapping = lectureCategoryService.saveMapping(lecture.getLectureId(), category.getCategoryId());

        // then
        assertThat(savedMapping).isNotNull();
        assertThat(savedMapping.getLecture().getTitle()).isEqualTo("테스트 강의");
        assertThat(savedMapping.getCategory().getName()).isEqualTo("국어");

        System.out.println("매핑 성공: " + savedMapping);
    }

}