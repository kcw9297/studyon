package studyon.app.layer.domain.lecture_like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studyon.app.layer.domain.lecture_like.LectureLike;

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
    Long countByLecture_LectureId(Long lectureId);

    // 회원별 좋아요 리스트 (관심목록)
    @Query("""
       SELECT ll
       FROM LectureLike ll
       JOIN FETCH ll.lecture lec
       JOIN FETCH lec.teacher t
       JOIN FETCH t.member m
       WHERE ll.member.memberId = :memberId
    """)
    List<LectureLike> findByMemberIdWithLectureTeacherMember(@Param("memberId") Long memberId);

    @Query("""
    SELECT ll
    FROM LectureLike ll
    JOIN FETCH ll.lecture l
    JOIN FETCH l.teacher t
    JOIN FETCH t.member m
    WHERE ll.member.memberId = :memberId
      AND UPPER(l.subject) = :subject
""")
    List<LectureLike> findByMemberIdWithLectureAndSubjectString(
            @Param("memberId") Long memberId,
            @Param("subject") String subject
    );

    /**
     *  좋아요 삭제 (회원 + 강의 조합)
     */
    void deleteByMember_MemberIdAndLecture_LectureId(Long memberId, Long lectureId);

    /**
     * [1] 회원별 좋아요 목록 조회
     */
    @Query("""
        SELECT ll
        FROM LectureLike ll
        JOIN FETCH ll.lecture lec
        JOIN FETCH lec.teacher t
        JOIN FETCH t.member m
        WHERE ll.member.memberId = :memberId
        ORDER BY lec.subject, lec.title
    """)
    List<LectureLike> findByMemberId(@Param("memberId") Long memberId);


    /**
     * 회원 + 과목별 좋아요 목록 조회
     */
    @Query("""
        SELECT ll
        FROM LectureLike ll
        JOIN FETCH ll.lecture lec
        JOIN FETCH lec.teacher t
        JOIN FETCH t.member m
        WHERE ll.member.memberId = :memberId
          AND UPPER(lec.subject) = UPPER(:subject)
        ORDER BY lec.title
    """)
    List<LectureLike> findByMemberIdAndSubject(
            @Param("memberId") Long memberId,
            @Param("subject") String subject
    );

}
