package studyon.app.layer.domain.lecture.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import studyon.app.common.enums.Difficulty;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LectureServiceTest {
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @DisplayName("최근 강의 목록 조회 테스트 - publishDate 내림차순")
    void testFindRecentLectures() throws InterruptedException {

        Teacher teacher = teacherRepository.findAll().get(0);

        lectureRepository.save(Lecture.builder()
                .teacher(teacher)
                .title("L1")
                .price(10000.0)
                .difficulty(Difficulty.BASIC)
                .build());

        Thread.sleep(1000); // publishDate 차이를 주기 위해 1초 대기

        lectureRepository.save(Lecture.builder()
                .teacher(teacher)
                .title("L2")
                .price(15000.0)
                .difficulty(Difficulty.ADVANCED)
                .build());

        List<Lecture> result = lectureRepository.findAllByOrderByPublishDateDesc();
        assertThat(result.get(0).getTitle()).isEqualTo("L2");

    }

    @Test
    @DisplayName("최신순 강의 5개 조회 테스트")
    void testFindTop5ByOrderByPublishDateDesc() throws InterruptedException {
        Teacher teacher = teacherRepository.findAll().get(0);

        // 임시 테스트 데이터 6개 생성
        for (int i = 1; i <= 6; i++) {
            lectureRepository.save(Lecture.builder()
                    .teacher(teacher)
                    .title("Lecture " + i)
                    .price(10000.0 + i)
                    .difficulty(Difficulty.BASIC)
                    .build());
            Thread.sleep(300);  // publishDate 차이를 주기 위해 잠시 대기
        }

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Lecture> page = lectureRepository.findByOrderByPublishDateDesc(pageRequest);
        List<Lecture> top5 = page.getContent();

        assertThat(top5).hasSize(5);
        assertThat(top5.get(0).getTitle()).isEqualTo("Lecture 6"); // 최신
        assertThat(top5.get(4).getTitle()).isEqualTo("Lecture 2"); // 가장 오래된(6개 중 2번째)

        // 출력 확인
        System.out.println("=== 최신순 TOP 5 ===");
        top5.forEach(l -> System.out.println(l.getTitle() + " / " + l.getPublishDate()));
    }

}