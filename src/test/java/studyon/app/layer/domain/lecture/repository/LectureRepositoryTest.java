package studyon.app.layer.domain.lecture.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LectureRepositoryTest {
    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @DisplayName("최근 강의 목록 조회 테스트 - publishDate 내림차순")
    void findRecentLectures() {
        // given(일단 더미 데이터 넣어놨으니 패스)

        // when
        List<Lecture> recentLectures = lectureRepository.findAllByOrderByPublishDateDesc();

        // then
        assertThat(recentLectures).isNotEmpty();
        assertThat(recentLectures.size()).isGreaterThanOrEqualTo(10);

        for (Lecture lecture : recentLectures) {
            System.out.println("강의 : " + lecture.getTitle());
        }
    }
}