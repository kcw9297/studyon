package studyon.app.layer.domain.home.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.repository.PaymentRepository;
import studyon.app.layer.domain.teacher.Teacher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-24) : khj00 최초 작성
 */

/**
 * 관리자 메인 페이지 대시보드 인터페이스 구현체
 * @version 1.0
 * @author khj00
 */

@Service
@RequiredArgsConstructor
@Transactional
public class AdminHomeServiceImpl implements AdminHomeService {
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Long readAllMemberCount() {
        return memberRepository.count();
    }


    @Override
    public Long readTodayNewMemberCount() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();   // 오늘 기준 시작 시간
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1); // 내일 시작 시간 - 1나노초 (어제의 끝)

        return memberRepository.countMembersJoinedBetween(startOfDay, endOfDay);
    }

    @Override
    public Long readActiveMemberCount() {
        // [1] 현재 시각
        LocalDateTime now = LocalDateTime.now();
        // [2] 일주일 전 현재 시각
        LocalDateTime sevenDaysAgo = now.minusDays(7);
        // [3] 일주일 시간 계산 후 리턴
        return memberRepository.countByLastLoginAtBetween(sevenDaysAgo, now);
    }

    @Override
    public Long readMonthSales() {
        // [1] 현재 시각
        LocalDateTime end = LocalDateTime.now();
        // [2] 이번 달 시작 (1일)
        LocalDateTime start = LocalDate.now()
                .withDayOfMonth(1)
                .atStartOfDay();
        // [3] 이번 달 총 매출액수 리턴
        return paymentRepository.findAllSales(start, end);
    }

    @Override
    public List<PaymentDTO.TeacherSales> calculateMonthlySales() {
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1).minusNanos(1);

        // [1] 이번 달 결제 내역 조회
        List<Payment> payments = paymentRepository.findTeacherSalesBetween(start, end);
        if (payments.isEmpty()) return List.of();

        // [2] 강사별 매출 합계 계산
        Map<Long, PaymentDTO.TeacherSales> salesMap = new HashMap<>();
        // [3] 이번 달 결제 내역 전체 반복
        for (Payment p : payments) {
            // [3-1] 결제 하나당 관련된 강사 정보 및 결제 금액을 추출
            Long teacherId = p.getLecture().getTeacher().getTeacherId();
            String nickname = p.getLecture().getTeacher().getMember().getNickname();
            Double amount = p.getPaidAmount().doubleValue();

            // [4] salesMap에 강사별로 매출 누적
            // compute()는 key가 없으면 새로 등록하고, 있으면 갱신함.
            salesMap.compute(teacherId, (id, existing) -> {
                if (existing == null) {
                    // 아직 이 강사의 첫 결제라면 새 DTO 생성
                    return PaymentDTO.TeacherSales.builder()
                            .teacherId(id)
                            .nickname(nickname)
                            .totalSales(amount)
                            .build();
                }
                // 이미 존재하면 기존 매출 + 이번 금액을 더해 누적
                else {
                    existing.setTotalSales(existing.getTotalSales() + amount);
                    return existing;
                }
            });
        }
        // [5] Map → List로 변환하여 반환
        // (각 강사별 매출 DTO를 리스트 형태로 변환)
        return new ArrayList<>(salesMap.values());
    }


    @Override
    public PaymentDTO.TeacherSales selectTopTeacher() {
        // [1] 기록 불러와서
        List<PaymentDTO.TeacherSales> list = calculateMonthlySales();
        // [2] 비어있는지 체크하고
        if (list.isEmpty()) return null;
        // [3] TOP 데이터 불러오기
        return list.stream()
                // 내부적으로 전달받은 getTotalSales()로 값을 추출 비교
                // Comparator.comparing() : 조건을 부여하여 정렬하는 메소드 -> 여기서는 TotalSales 기준으로 정렬함
                .max(Comparator.comparing(PaymentDTO.TeacherSales::getTotalSales))
                .orElse(null);
    }


    @Override
    public Long readAllLectureCount() {
        // [1] 총 강의 수 리턴
        return lectureRepository.count();
    }
}
