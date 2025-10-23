package studyon.app.layer.domain.member_lecture.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.member_lecture.MemberLectureDTO;
import studyon.app.layer.domain.member_lecture.MemberLecture;
import studyon.app.layer.domain.member_lecture.repository.MemberLectureRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberLectureService {

    private final MemberLectureRepository memberLectureRepository;

    @Transactional(readOnly = true)
    public List<MemberLectureDTO.Read> getLecturesByMemberAndSubject(Long memberId, String subject) {
        List<MemberLecture> list;

        if ("all".equalsIgnoreCase(subject)) {
            list = memberLectureRepository.findByMember_MemberId(memberId);
        } else {
            try {
                Subject subjectEnum = Subject.valueOf(subject.toUpperCase());
                list = memberLectureRepository.findByMember_MemberIdAndLecture_Subject(memberId, subjectEnum);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("잘못된 과목 파라미터입니다: " + subject);
            }
        }

        return list.stream()
                .map(m -> MemberLectureDTO.Read.builder()
                        .memberLectureId(m.getMemberLectureId())
                        .lectureId(m.getLecture().getLectureId())
                        .lectureTitle(m.getLecture().getTitle())
                        .teacherName(m.getLecture().getTeacher().getMember().getNickname())
                        .subject(m.getLecture().getSubject().name()) // ← enum → 문자열 변환
                        .progress(m.getProgress())
                        .completed(m.getCompleted())
                        .build())
                .collect(Collectors.toList());
    }
}
