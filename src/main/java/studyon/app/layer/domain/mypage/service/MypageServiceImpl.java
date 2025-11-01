package studyon.app.layer.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture_like.LectureLike;
import studyon.app.layer.domain.lecture_like.LectureLikeDTO;
import studyon.app.layer.domain.lecture_like.repository.LectureLikeRepository;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.repository.PaymentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MypageServiceImpl implements MypageService {

    private final LectureLikeRepository lectureLikeRepository;
    private final PaymentRepository paymentRepository;

    /**
     * [1] 회원별 좋아요 목록 조회 (과목 필터 가능)
     */
    @Override
    public List<LectureLikeDTO.Read> getLikesByMemberAndSubject(Long memberId, String subject) {
        List<LectureLike> likes;

        if (subject == null || subject.equalsIgnoreCase("all")) {
            likes = lectureLikeRepository.findByMemberId(memberId);
        } else {
            likes = lectureLikeRepository.findByMemberIdAndSubject(memberId, subject.toUpperCase());
        }

        return likes.stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }

    /**
     * [2] 좋아요 삭제
     */
    @Override
    public void deleteLike(Long memberId, Long lectureId) {
        // [1] 회원 + 강의 조합으로 좋아요 엔티티 조회
        List<LectureLike> likes = lectureLikeRepository.findByMemberId(memberId)
                .stream()
                .filter(like -> like.getLecture().getLectureId().equals(lectureId))
                .toList();
        if (likes.isEmpty()) {
            log.warn("[MYPAGE] 삭제 실패: 존재하지 않는 좋아요 - memberId={}, lectureId={}", memberId, lectureId);
            return;
        }
        // [2] 좋아요 삭제 수행
        likes.forEach(lectureLikeRepository::delete);

        log.info("[MYPAGE] 좋아요 삭제 완료 - memberId={}, lectureId={}", memberId, lectureId);
    }

    /**
     * [3] 마이페이지 - 구매내역 조회 메소드
     */
    @Override
    public List<PaymentDTO.Read> getPaymentsByMemberAndDate(Long memberId, LocalDate since, LocalDate until) {
        return paymentRepository.findWithMemberAndLectureByMemberIdAndDateRange(
                memberId,
                since.atStartOfDay(),
                until.atTime(23, 59, 59)
        ).stream().map(DTOMapper::toReadDTO).collect(Collectors.toList());
    }
}
