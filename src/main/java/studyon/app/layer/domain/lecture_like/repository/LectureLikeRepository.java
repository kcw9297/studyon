package studyon.app.layer.domain.lecture_like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture_like.LectureLike;

import java.util.List;

public interface LectureLikeRepository extends JpaRepository<LectureLike, Long> {
    List<LectureLike> findByMember_MemberId(Long memberId);
}
