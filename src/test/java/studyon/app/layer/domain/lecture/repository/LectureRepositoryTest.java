package studyon.app.layer.domain.lecture.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import studyon.app.common.enums.Difficulty;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LectureRepositoryTest {
    @Autowired
    private LectureRepository lectureRepository;

    /*

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
        Page<Lecture> page = lectureRepository.findTop5ByOrderByPublishDateDesc(pageRequest);
        List<Lecture> top5 = page.getContent();

        assertThat(top5).hasSize(5);
        assertThat(top5.get(0).getTitle()).isEqualTo("Lecture 6"); // 최신
        assertThat(top5.get(4).getTitle()).isEqualTo("Lecture 2"); // 가장 오래된(6개 중 2번째)

        // 출력 확인
        System.out.println("=== 최신순 TOP 5 ===");
        top5.forEach(l -> System.out.println(l.getTitle() + " / " + l.getPublishDate()));
    }

     */

    @Test
    @DisplayName("특정 강의 상세 정보 불러오기 테스트")
    @Transactional
    void LoadLectureInfo() {
        // given
        Long lectureId = 1L;

        // when
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 강의가 존재하지 않습니다."));

        // then
        System.out.println("===== [강의 기본 정보] =====");
        System.out.println("강의 제목: " + lecture.getTitle());
        System.out.println("강의 설명: " + lecture.getDescription());
        System.out.println("난이도: " + lecture.getDifficulty());
        System.out.println("가격: " + lecture.getPrice());
        System.out.println("총 수강생 수: " + lecture.getTotalStudents());
        System.out.println("평균 평점: " + lecture.getAverageRate());
        System.out.println("등록일: " + lecture.getPublishDate());
        System.out.println();

        System.out.println("===== [강사 정보] =====");
        Teacher teacher = lecture.getTeacher();
        System.out.println("강사 ID: " + teacher.getTeacherId());
        System.out.println("담당 과목: " + teacher.getSubject());
        System.out.println("강사 소개: " + teacher.getDescription());
        System.out.println("총 리뷰 수: " + teacher.getTotalReview());
        System.out.println("총 학생 수: " + teacher.getTotalStudents());

        System.out.println();

        System.out.println("===== [강사 기본 회원 정보] =====");
        if (teacher.getMember() != null) {
            System.out.println("닉네임: " + teacher.getMember().getNickname());
            System.out.println("이메일: " + teacher.getMember().getEmail());
            System.out.println("역할: " + teacher.getMember().getRole());
        } else {
            System.out.println("Member 정보가 없습니다.");
        }

        // 검증 (title, teacher.subject 같은 주요 필드가 null 아님을 보장)
        assertThat(lecture.getTitle()).isNotNull();
        assertThat(lecture.getTeacher()).isNotNull();
        assertThat(lecture.getTeacher().getSubject()).isNotNull();
    }
}