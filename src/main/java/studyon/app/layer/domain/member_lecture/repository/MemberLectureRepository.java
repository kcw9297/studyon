package studyon.app.layer.domain.member_lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.member_lecture.MemberLecture;

import java.util.List;

public interface MemberLectureRepository extends JpaRepository<MemberLecture, Long> {
    List<MemberLecture> findByMember_MemberId(Long memberId);
    List<MemberLecture> findByMember_MemberIdAndLecture_Subject(Long memberId, Subject subject);
}