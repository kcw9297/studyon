package studyon.app.layer.domain.lecture_like.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.layer.domain.lecture_like.LectureLike;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class LectureLikeRepositoryTest {

    @Autowired
    private LectureLikeRepository lectureLikeRepository;

    @Test
    @DisplayName("특정 회원의 좋아요 목록 불러오기 테스트")
    void findLikesByMemberId() {
        // given
        Long memberId = 4L;

        // when
        List<LectureLike> likes = lectureLikeRepository.findByMember_MemberId(memberId);

        // then
        assertThat(likes).isNotEmpty();
        System.out.println("=== 회원 " + memberId + "의 좋아요 목록 ===");
        likes.forEach(like -> {
            System.out.println("좋아요 ID: " + like.getLectureLikeId()
                + " | 강의명: " + like.getLecture().getTitle()
                + " | 강의ID: " + like.getLecture().getLectureId());
        });

        // 검증 : 강의 ID 중복이 없나 확인
        Set<Long> lectureIds = likes.stream()
                .map(like -> like.getLecture().getLectureId())
                .collect(Collectors.toSet());
        assertThat(lectureIds.size()).isEqualTo(likes.size()); //  중복 없음
    }
}