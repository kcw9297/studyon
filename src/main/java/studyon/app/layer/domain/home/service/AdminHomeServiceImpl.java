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
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public List<PaymentDTO.Read> calculateMonthlySales() {
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1).minusNanos(1);

        List<Payment> teacherSales = paymentRepository.findTeacherSalesBetween(start, end);
        List<PaymentDTO.Read> dtoList = teacherSales.stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());

        return dtoList;
    }

    @Override
    public Long readAllLectureCount() {
        // [1] 총 강의 수 리턴
        return lectureRepository.count();
    }

}
