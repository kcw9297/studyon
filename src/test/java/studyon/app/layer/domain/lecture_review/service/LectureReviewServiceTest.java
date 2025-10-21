package studyon.app.layer.domain.lecture_review.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LectureReviewServiceTest {

    @Autowired
    private LectureReviewService lectureReviewService;

    /*
    @Test
    @DisplayName("선생님별 강의 리뷰 최신순 조회")
    void testGetReviewsByTeacherId() {
        Long teacherId = 2L;

        List<LectureReviewDTO.Read> reviews = lectureReviewService.readReviewsByTeacherId(teacherId);

        assertThat(reviews).isNotEmpty();
        assertThat(reviews).isSortedAccordingTo(
                (r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt())
        );

        reviews.forEach(r -> System.out.println(
                r.getMemberId() + " / " +
                r.getContent()
        ));
    }

     */
}