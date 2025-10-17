package studyon.app.layer.domain.lecture_review.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LectureReviewRepositoryTest {

    @Autowired
    private LectureReviewRepository lectureReviewRepository;

    @Test
    @DisplayName("강의 리뷰 생성 테스트")
    void createReview() {

    }
}