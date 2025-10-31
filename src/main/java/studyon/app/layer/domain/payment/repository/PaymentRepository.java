package studyon.app.layer.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studyon.app.layer.domain.payment.Payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-26) : khj00 통계메소드 추가
 *  ▶ ver 1.2 (2025-10-28) : kcw97 회원과 함께 조인하는 패치조인 메소드 추가
 *  ▶ ver 1.3 (2025-10-29) : phj: 마이페이지 - 구매내역 추가
 */

/**
 * 결제정보 조작 클래스
 * @version 1.3
 * @author kcw97
 */

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * 회원 정보와 함께 조회 특정 회원이 결제한 목록 조회
     */

    @Query("""
        SELECT p
        FROM Payment p
        LEFT JOIN FETCH p.member m
        WHERE m.memberId = :memberId
    """)
    List<Payment> findWithMemberByMemberId(Long memberId);

    /**
     * 회원 정보화 함께 결제정보 단일 조회
     */

    @Query("""
        SELECT p
        FROM Payment p
        LEFT JOIN FETCH p.member m
        WHERE p.paymentId = :paymentId
    """)
    Optional<Payment> findWithMemberById(Long paymentId);


    /**
     * 기간 내 결제정보 조회
     */
    @Query("""
        SELECT p
        FROM Payment p
        LEFT JOIN FETCH p.member m
        WHERE p.cdate <= :endDate
    """)
    List<Payment> findWithinDate(LocalDateTime endDate);


    /**
     * 이번 달 강사별 매출 합계 조회
     * COALESCE를 써서 매출이 없을 때 null 대신 0을 반환
     */
    @Query("""
        SELECT DISTINCT p
        FROM Payment p
        JOIN FETCH p.lecture l
        JOIN FETCH l.teacher t
        JOIN FETCH t.member m
        WHERE p.isRefunded = false
          AND p.cdate BETWEEN :start AND :end
    """)
    List<Payment> findTeacherSalesBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


    /**
     * 이번 달 매출 합계 조회 (환불 제외)
     */
    @Query("""
       SELECT COALESCE(SUM(p.paidAmount), 0)
       FROM Payment p
       WHERE p.isRefunded = false AND p.cdate BETWEEN :start AND :end
    """)
    Long findAllSales(@Param("start") LocalDateTime start,
                      @Param("end") LocalDateTime end
    );

    /* 마이페이지 - 구매내역 */
    @Query("""
        SELECT p
        FROM Payment p
        LEFT JOIN FETCH p.member m
        LEFT JOIN FETCH p.lecture l
        WHERE m.memberId = :memberId
          AND p.cdate BETWEEN :start AND :end
        ORDER BY p.cdate DESC
    """)
    List<Payment> findWithMemberAndLectureByMemberIdAndDateRange(
            @Param("memberId") Long memberId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END
        FROM Payment p
        WHERE p.member.memberId = :memberId AND p.lecture.lectureId = :lectureId AND p.isRefunded = :isRefunded
    """)
    boolean existsByMemberIdAndLectureIdAndIsRefunded(Long memberId, Long lectureId, Boolean isRefunded);
}
