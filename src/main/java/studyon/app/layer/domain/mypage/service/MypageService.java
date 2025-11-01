package studyon.app.layer.domain.mypage.service;

import studyon.app.layer.domain.lecture_like.LectureLike;
import studyon.app.layer.domain.lecture_like.LectureLikeDTO;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.PaymentDTO;

import java.time.LocalDate;
import java.util.List;

public interface MypageService {
    List<LectureLikeDTO.Read> getLikesByMemberAndSubject(Long memberId, String subject);

    void deleteLike(Long memberId, Long lectureId);

    List<PaymentDTO.Read> getPaymentsByMemberAndDate(Long memberId, LocalDate since, LocalDate until);
}
