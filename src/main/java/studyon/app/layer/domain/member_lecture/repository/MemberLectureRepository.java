package studyon.app.layer.domain.member_lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member_lecture.MemberLecture;

import java.util.List;
import java.util.Optional;

public interface MemberLectureRepository extends JpaRepository<MemberLecture, Long> {
    List<MemberLecture> findByMember_MemberId(Long memberId);


    @Query("""
        SELECT ml
        FROM MemberLecture ml
        WHERE ml.member.memberId = :memberId AND ml.lecture.lectureId = :lectureId
    """)
    Optional<MemberLecture> findByMemberIdAndLectureId(Long memberId, Long lectureId);

    @Query("""
        SELECT ml
        FROM MemberLecture ml
        LEFT JOIN FETCH ml.lecture
        WHERE ml.member.memberId = :memberId AND ml.lecture.lectureId = :lectureId
    """)
    Optional<MemberLecture> findWithLectureByMemberIdAndLectureId(Long memberId, Long lectureId);
    List<MemberLecture> findByMember_MemberIdAndLecture_Subject(Long memberId, Subject subject);
    boolean existsByMemberAndLecture(Member member, Lecture lecture);
    boolean existsByMember_MemberIdAndLecture_LectureId(Long memberId, Long lectureId);

    //TeacherId를 통해서 총 수강생 수를 구함
    @Query("""
        SELECT COUNT(DISTINCT ml.member.memberId)
        FROM MemberLecture ml
        JOIN ml.lecture l
        WHERE l.teacher.teacherId = :teacherId
    """)
    Long countDistinctStudentsByTeacherId(Long teacherId);



}