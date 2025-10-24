package studyon.app.layer.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.PaymentDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMember_MemberId(Long memberId);

    /**
     * 이번 달 강사별 매출 합계 조회
     * COALESCE를 써서 매출이 없을 때 null 대신 0을 반환
     * 각 행은 PaymentDTO.TeacherSales(강사ID, 닉네임, 매출합계) 형태로 반환
     * new studyon.app.layer.domain.payment.PaymentDTO$TeacherSales - List 형태의 객체 반환을 위해 사용
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
    Long findAllSales(@Param("start") LocalDate start,
                      @Param("end") LocalDate end);
}
