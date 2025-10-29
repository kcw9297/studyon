package studyon.app.layer.domain.payment.service;

import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.PaymentSession;

import java.time.Duration;
import java.util.List;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-28) : kcw97 결제 메소드명 수정 및 메소드 추가
 */

/**
 * 결제 서비스 인터페이스
 * @version 1.1
 * @author khj00
 */

public interface PaymentService {

    /**
     * 특정 회원의 결제 목록 조회
     * @param rq 검색 요청
     * @param prq 페이징 요청
     * @return 페이징된 결제정보 리스트
     */
    Page.Response<PaymentDTO.Read> readPagedList(PaymentDTO.Search rq, Page.Request prq);


    /**
     * 특정 회원의 N개월 내 목록 조회
     * @param days 기준 시간
     * @return N개월 내의 결제정보 리스트
     */
    List<PaymentDTO.Read> readRecentList(Duration days);


    /**
     * 특정 결제정보 조회
     * @param paymentId 대상 결제번호
     * @param profile   회원 프로필 정보
     * @return 단일 결제정보
     */
    PaymentDTO.Read read(Long paymentId, MemberProfile profile);


    /**
     * 결제 페이지 접근
     * @param memberId  주문 대상 회원번호
     * @param lectureId 대상 강의번호
     * @return 검증 후 생성된 결제세션 데이터
     */
    PaymentSession access(Long memberId, Long lectureId);


    /**
     * 결제 수행
     * @param memberId  주문 대상 회원번호
     * @param lectureId 대상 강의번호
     * @param token 결제 토큰번호
     * @return 현재 결제를 진행중인 결제 세션정보
     */
    PaymentSession verify(Long memberId, Long lectureId, String token);


    /**
     * 결제 수행
     * @param dto 결제 요청 데이터
     * @return 수행된 결제정보
     */
    PaymentDTO.Read pay(PaymentDTO.Pay dto);

    /**
     * 강의 환불 (현재는 구매가 하나만 가능하여, 모두 환불)
     * @param paymentId    결제번호 (서버 내에서 사용하는 번호)
     * @param refundReason 환불 사유
     */
    void refund(Long paymentId, String refundReason);
}
