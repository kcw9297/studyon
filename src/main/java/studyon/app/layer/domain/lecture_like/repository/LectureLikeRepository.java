package studyon.app.layer.domain.lecture_like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture_like.LectureLike;
import studyon.app.layer.domain.member.Member;

import java.util.*;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-28) : phj 상태 확인, 삭제 기능 추가
 */

/**
 * 강의 좋아요(찜) 레포지토리 인터페이스
 * @version 1.1
 * @author phj
 */

public interface LectureLikeRepository extends JpaRepository<LectureLike, Long> {
    // 회원과 강의로 좋아요 존재 여부
    boolean existsByMember_MemberIdAndLecture_LectureId(Long memberId, Long lectureId);

    // 회원과 강의로 좋아요 객체 조회 (삭제용)
    Optional<LectureLike> findByMember_MemberIdAndLecture_LectureId(Long memberId, Long lectureId);

    // 강의별 좋아요 수
    long countByLecture_LectureId(Long lectureId);

    // 회원별 좋아요 리스트 (관심목록)
    List<LectureLike> findByMember_MemberId(Long memberId);
}
