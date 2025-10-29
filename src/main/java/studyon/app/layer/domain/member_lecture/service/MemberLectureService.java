package studyon.app.layer.domain.member_lecture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.member_lecture.MemberLectureDTO;
import studyon.app.layer.domain.member_lecture.MemberLecture;
import studyon.app.layer.domain.member_lecture.repository.MemberLectureRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberLectureService {

    private final MemberLectureRepository memberLectureRepository;
    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;

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
    @Transactional
    public void enroll(Long memberId, Long lectureId) {
        // 1️⃣ 회원 & 강의 유효성 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + memberId));

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다. id=" + lectureId));

        // 2️⃣ 중복 등록 방지
        boolean exists = memberLectureRepository.existsByMemberAndLecture(member, lecture);
        if (exists) {
            log.warn("🚫 이미 등록된 강의입니다. memberId={}, lectureId={}", memberId, lectureId);
            return;
        }

        // 3️⃣ 등록 처리
        MemberLecture memberLecture = MemberLecture.builder()
                .member(member)
                .lecture(lecture)
                .build();

        memberLectureRepository.save(memberLecture);
        log.info("✅ member_lecture 등록 완료 - memberId={}, lectureId={}", memberId, lectureId);
    }

}
