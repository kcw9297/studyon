package studyon.app.infra.cache.manager;

import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.infra.mail.dto.MailVerifyRequest;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-17) : kcw97 로그아웃 사용자 캐시 제거기능 추가
 */

/**
 * 캐시 정보 관리
 * @version 1.1
 * @author kcw97
 */
public interface CacheManager {

    /**
     * key 삭제
     * @param key 삭제 대상 Redis Key
     */
    void removeKey(String key);

    /**
     * 현재 로그인 세션정보 기록
     * @param memberId 로그인 회원번호
     * @param sessionId 로그인 세션번호
     */
    void recordLogin(Long memberId, String sessionId);

    /**
     * 로그아웃 회원 정보 제거
     * @param memberId 로그인 회원번호
     * @param sessionId 로그인 세션번호
     */
    void removeLogout(Long memberId, String sessionId);


    /**
     * 가입/수정 시 그 회원의 프로필 정보 저장
     * @param profile 회원 프로필 정보
     */
    void saveProfile(MemberProfile profile);

    /**
     * 프로필 정보 제거 (회원탈퇴, 회원정보 수정 등의 사유)
     * @param memberId 삭제 대상 회원번호
     */
    void removeProfile(Long memberId);

    /**
     * 최근에 검색한 강의 검색어 기록
     * @param memberId 검색 수행 회원
     * @param keyword 검색 키워드
     */
    void recordLatestSearch(Long memberId, String keyword);

    /**
     * 인증 이메일 정보 기록
     * @param mailVerifyRequest 인증 메일 요청
     * @param sessionId 요청 세션 아이디
     */
    boolean recordVerifyMail(MailVerifyRequest mailVerifyRequest, String sessionId);

    /**
     * 회원 프로필 조회
     * @param memberId 조회 대상 회원번호
     * @return 조회된 회원 프로필 반환
     */
    MemberProfile getProfile(Long memberId);

    /**
     * 최근 검색어 목록 조회
     * @param memberId 조회 대상 회원번호
     * @return 회원이 최근에 검색한 검색어 목록 반환
     */
    List<String> getLatestSearchList(Long memberId);

    /**
     * 이메일 인증 요청 조회
     * @param sessionId 요청 세션 아이디
     */
    MailVerifyRequest getMailRequest(String sessionId);

}
