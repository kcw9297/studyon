package studyon.app.layer.domain.home.service;

import studyon.app.layer.domain.payment.PaymentDTO;

import java.util.List;
import java.util.Optional;

public interface AdminHomeService {
    /**
     * (관리자 통계용) 총 회원 수
     * @return 총 회원 수
     */
    Long readAllMemberCount();


    /**
     * (관리자 통계용) 오늘 신규 회원 수
     * @return 오늘 신규 회원 수
     */
    Long readTodayNewMemberCount();

    /**
     * (관리자 통계용) 오늘 신규 회원 수
     * @return 오늘 신규 회원 수
     */
    Long readActiveMemberCount();

    /**
     * (관리자 통계용) 총 강의 수 반환
     * @return 총 강의 수
     */
    Long readAllLectureCount();

    /**
     * (관리자 통계용) 한 달 총 매출
     * @return 한 달 총 매출액수
     */
    Long readMonthSales();

    /**
     * (관리자 통계용) 선생님 별 매출 액수
     * @return 선생님 별 매출액수
     */
    List<PaymentDTO.Read> calculateMonthlySales();

}
