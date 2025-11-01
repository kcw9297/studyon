package studyon.app.infra.cache.manager;

import java.time.Duration;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-17) : kcw97 로그아웃 사용자 캐시 제거기능 추가
 *  ▶ ver 1.2 (2025-10-22) : kcw97 게시글 캐시기능 추가
 */

/**
 * 캐시 정보 관리
 * @version 1.2
 * @author kcw97
 */
public interface CacheManager {

    /**
     * 현재 로그인 세션정보 기록
     * @param memberId 로그인 회원번호
     * @param sessionId 로그인 세션번호
     */
    void recordLogin(Long memberId, String sessionId);

    /**
     * 로그아웃 회원 정보 제거
     * @param memberId 로그인 회원번호
     */
    void removeLogout(Long memberId);


    /**
     * 최근에 검색한 강의 검색어 기록
     * @param memberId 검색 수행 회원
     * @param keyword 검색 키워드
     */
    void recordRecentKeyword(Long memberId, String keyword);

    /**
     * 최근 검색어 목록 조회
     * @param memberId 조회 대상 회원번호
     * @return 회원이 최근에 검색한 검색어 목록 반환
     */
    List<String> getRecentKeywords(Long memberId);


    /**
     * 인증 요청 기록
     * @param target  인증 대상 (이메일, 전화번호, ...)
     * @param token   인증 토큰 값 (UUID, ...)
     * @param timeout 만료 시간
     */
    boolean recordAuthRequest(String target, String token, Duration timeout);


    /**
     * 인증 요청 기록 (저장 데이터 포함)
     * @param target      인증 대상 (이메일, 전화번호, ...)
     * @param authRequest 인증 요청 객체
     * @param timeout     만료 시간
     * @param token 인증 토큰 값 (UUID, ...)
     */
    boolean recordAuthRequest(String target, String token, Duration timeout, Object authRequest);


    /**
     * 인증 요청 검증 (인증 요청이 정상 존재하는지 검증)
     * @param token 인증 토큰 값
     */
    boolean isAuthRequestValid(String token);


    /**
     * 인증 요청 검증 후, 유효할 시 데이터 조회
     * @param token 인증 토큰 값
     * @param type 인증 요청 객체 Class Type
     * @return 유효할 시 데이터, 유효하지 않을 시 null
     */
    <T> T getAuthRequest(String token, Class<T> type);


    /**
     * 인증 요청 삭제
     * @param token 인증 토큰 값
     */
    void removeAuthRequest(String token);


    /**
     * 결제 인증 토큰 정보를 담은 요청 저장 (기존 요청이 있다면 새롭게 대체하여 토큰 값 변경)
     * @param memberId       결제 대상 회원번호
     * @param lectureId      결제 대상 강의번호
     * @param paymentRequest 결제 정보 객체 (토큰 정보 포함)
     */
     void recordPaymentRequest(Long memberId, Long lectureId, Object paymentRequest);


    /**
     * 결제 인증 토큰 정보를 담은 요청 저장 (기존 요청이 있다면 새롭게 대체하여 토큰 값 변경)
     * @param memberId  결제 대상 회원번호
     * @param lectureId 결제 대상 강의번호
     * @param dataType  저장된 결제 객체 타입
     * @return 만료되지 않은 경우, 저장된 결제 정보 (만료 시 null)
     */
     <T> T getPaymentRequest(Long memberId, Long lectureId, Class<T> dataType);


    /**
     * 결제 인증 토큰 정보를 담은 요청 저장 (조회 후 인증요청 삭제)
     * @param memberId  결제 대상 회원번호
     * @param lectureId 결제 대상 강의번호
     * @param dataType  저장된 결제 객체 타입
     * @return 만료되지 않은 경우, 저장된 결제 정보 (만료 시 null)
     */
    <T> T getAndDeletePaymentRequest(Long memberId, Long lectureId, Class<T> dataType);

}
