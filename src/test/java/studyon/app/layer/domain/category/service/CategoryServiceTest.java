package studyon.app.layer.domain.category.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리별 강의 조회 테스트")
    void testGetLecturesByCategoryId() {
        Long categoryId = 2L;

        List<LectureDTO.Read> lectures = categoryService.getLecturesByCategoryId(categoryId);

        System.out.println("조회된 강의 개수: " + lectures.size());
        lectures.forEach(l -> System.out.println("▶ " + l.getTitle()));

        assertThat(lectures).isNotEmpty();
        lectures.forEach(l -> System.out.println("강의명 : " + l.getTitle()));
    }

    @Test
    @DisplayName("카테고리별 선생님 조회 테스트")
    void testGetTeachersByCategoryId() {
        Long categoryId = 1L;

        List<TeacherDTO.Read> teachers = categoryService.getTeachersByCategoryId(categoryId);

        assertThat(teachers).isNotEmpty();
        teachers.forEach(t -> System.out.println("선생님 : " + t.getSubject() + "/ ID: " + t.getTeacherId()));
    }
}