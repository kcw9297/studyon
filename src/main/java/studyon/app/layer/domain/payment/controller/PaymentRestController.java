package studyon.app.layer.domain.payment.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Param;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member_lecture.service.MemberLectureService;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.PaymentSession;
import studyon.app.layer.domain.payment.service.PaymentService;

import java.util.Map;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-24) : khs97 최초 작성
 *  ▶ ver 1.1 (2025-10-26) : kcw97 프로필 획득 로직 수정
 *  ▶ ver 1.2 (2025-10-29) : kcw97 결제 로직 추가
 */

/**
 * 강의 결제 처리 요청을 받는 클래스
 * @version 1.2
 * @author khs97
 */

@Slf4j
@RestController
@RequestMapping(Url.PAYMENTS_API)
@RequiredArgsConstructor
@Validated
public class PaymentRestController {

    private final PaymentService paymentService;

    /**
     * [GET] 페이징 검색
     */
    @GetMapping
    public ResponseEntity<?> readPagedList(HttpSession session, PaymentDTO.Search rq, Page.Request prq) {

        // [1] 회원번호 조회 후 삽입
        Long memberId = SessionUtils.getMemberId(session);
        rq.setStudentSearch(memberId); // 일반 학생용 검색

        // [2] 검색 수행
        Page.Response<PaymentDTO.Read> page = paymentService.readPagedList(rq, prq);

        // [3] 페이징 결과 반환
        return RestUtils.ok(page);
    }


    /**
     * [POST] 실제 결제 전 검증 (유효성 검사는 생략)
     * <br>유효성 검증, 로그인 및 권한 검증, 주문세션 검증, 강의 구매가능 상태 검증 등
     */
    @PostMapping
    public ResponseEntity<?> pay(HttpSession session, PaymentDTO.Pay rq) {

        // [1] 결제 회원정보 삽입
        Long memberId = SessionUtils.getMemberId(session);
        rq.setMemberId(memberId);

        // [2] 결제 수행
        PaymentDTO.Read result = paymentService.pay(rq);

        // [3] 결제 결과 및 결제 결과를 확인할 수 있는 주소 반환
        session.setAttribute(Param.VERIFIED, true);
        return RestUtils.ok(AppStatus.OK, "/payment/enroll-complete", result);
    }


    /**
     * [POST] 결제 페이지 접근 검증
     * <br>강의 구매상태 검증, 회원 권한 검증 등 수행 후, 주문세션 생성
     */
    @PostMapping("/access")
    public ResponseEntity<?> access(HttpSession session,
                                    @NotNull(message = "결제번호가 유효하지 않습니다.") Long lectureId) {

        // [1] 회원번호 조회
        Long memberId = SessionUtils.getMemberId(session);

        // [2] 접근 전 검증 & 결제세션 생성
        PaymentSession paymentRequest = paymentService.access(memberId, lectureId);

        // [3] 성공 응답 반환
        String redirect = "%s/enroll?lectureId=%s&token=%s".formatted(Url.PAYMENT, paymentRequest.getLectureId(), paymentRequest.getToken());
        return RestUtils.ok(AppStatus.OK, redirect);
    }

    /**
     * [POST] 실제 결제 전 검증
     * <br>유효성 검증, 로그인 및 권한 검증, 주문세션 검증, 강의 구매가능 상태 검증 등
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verify(HttpSession session, @Validated PaymentDTO.Pay rq) {

        // [1] 회원번호 조회
        Long memberId = SessionUtils.getMemberId(session);

        // [2] 결제 전 검증 수행
        paymentService.verify(memberId, rq.getLectureId(), rq.getToken());

        // [3] 검증 성공 시, 성공 응답 반환
        return RestUtils.ok();
    }


    /**
     * [GET] 단일 조회
     */
    @GetMapping("/{paymentId:[0-9]+}") // 정수형 숫자만 매핑
    public ResponseEntity<?> read(HttpSession session, @PathVariable Long paymentId) {

        // [1] 회원 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 조회 수행 (관리자 API가 아니면 본인만 정보 제공)
        PaymentDTO.Read data = paymentService.read(paymentId, profile);

        // [3] 조회 결과 반환
        return RestUtils.ok(data);
    }


}
