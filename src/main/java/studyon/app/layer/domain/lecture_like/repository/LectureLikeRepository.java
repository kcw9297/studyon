package studyon.app.layer.domain.lecture_like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture_like.LectureLike;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 좋아요(찜) 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureLikeRepository extends JpaRepository<LectureLike, Long> {
    List<LectureLike> findByMember_MemberId(Long memberId);
}
