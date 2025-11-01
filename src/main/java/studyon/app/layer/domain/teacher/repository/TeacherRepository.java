package studyon.app.layer.domain.teacher.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.teacher.Teacher;

import java.util.List;
import java.util.Optional;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-22) : khj00 : 지연 로딩 방지 @Query 추가
 */

/**
 * 선생님 레포지토리 인터페이스
 * @version 1.1
 * @author khj00
 */

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // [1] 과목별로 선생님 불러오는 메소드
    @Query("""
        SELECT t FROM Teacher t
        LEFT JOIN FETCH t.member
        WHERE t.subject = :subject ORDER BY t.teacherId ASC
    """)
    List<Teacher> findBySubjectWithMember(Subject subject);

    @Query("""
        SELECT t FROM Teacher t
        LEFT JOIN FETCH t.member
        WHERE t.member.memberId = :memberId
    """)
    Optional<Teacher> findByMemberIdWithMember(Long memberId);

    @Query("SELECT t FROM Teacher t JOIN FETCH t.member WHERE t.member.memberId = :memberId")
    Optional<Teacher> findByMember_MemberId(@Param("memberId") Long memberId);

    //Teacher Thumbnail MemberId로 조회
    @Query("""
    SELECT f.filePath
    FROM Member m
    JOIN File f ON m.profileImage.fileId = f.fileId
    WHERE m.memberId = :memberId
""")
    String findProfileImagePathByMemberId(@Param("memberId") Long memberId);


}
