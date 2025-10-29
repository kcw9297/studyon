package studyon.app.layer.domain.lecture_like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_like.LectureLike;
import studyon.app.layer.domain.lecture_like.LectureLikeDTO;
import studyon.app.layer.domain.lecture_like.repository.LectureLikeRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-28) : phj 최초 작성
 */

/**
 * 강의 서비스 인터페이스
 * @version 1.0
 * @author phj
 */

@Service
@RequiredArgsConstructor
@Transactional
public class LectureLikeServiceImpl implements LectureLikeService {
    private final LectureLikeRepository lectureLikeRepository;
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    @Override
    public void addLike(LectureLikeDTO.Write dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        Lecture lecture = lectureRepository.findById(dto.getLectureId())
                .orElseThrow(() -> new IllegalArgumentException("강의 없음"));

        if (!lectureLikeRepository.existsByMember_MemberIdAndLecture_LectureId(member.getMemberId(), lecture.getLectureId())) {
            lectureLikeRepository.save(new LectureLike(member, lecture));

            lecture.updateLikeCount(lecture.getLikeCount() + 1);
            lectureRepository.save(lecture);
        }
    }

    @Transactional
    @Override
    public void removeLike(LectureLikeDTO.Delete dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        Lecture lecture = lectureRepository.findById(dto.getLectureId())
                .orElseThrow(() -> new IllegalArgumentException("강의 없음"));

        lectureLikeRepository.findByMember_MemberIdAndLecture_LectureId(member.getMemberId(), lecture.getLectureId())
                .ifPresent(like -> {
                    lectureLikeRepository.delete(like);

                    lecture.updateLikeCount(lecture.getLikeCount() - 1);
                    lectureRepository.save(lecture);
                });
    }

    @Override
    public boolean isLiked(Long memberId, Long lectureId) {
        return lectureLikeRepository.existsByMember_MemberIdAndLecture_LectureId(memberId, lectureId);
    }

    @Override
    public long countByLectureId(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("강의 없음"))
                .getLikeCount();
    }

    @Override
    public List<LectureLike> getLikesByMember(Long memberId) {
        return lectureLikeRepository.findByMemberIdWithLectureTeacherMember(memberId);
    }

    @Override
    public List<LectureLike> getLikesByMemberAndSubject(Long memberId, String subject) {
        return lectureLikeRepository.findByMemberIdWithLectureAndSubject(memberId, subject);
    }

    @Override
    public void deleteLike(Long memberId, Long lectureId) {
        lectureLikeRepository.findByMember_MemberIdAndLecture_LectureId(memberId, lectureId)
                .ifPresent(lectureLikeRepository::delete);
    }

}
