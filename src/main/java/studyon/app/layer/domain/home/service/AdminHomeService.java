package studyon.app.layer.domain.home.service;

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
     * (관리자 통계용) 총 매출
     * @return 총 매출액수
     */
    Long readAllSales();
}
