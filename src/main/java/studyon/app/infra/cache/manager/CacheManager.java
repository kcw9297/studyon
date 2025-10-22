package studyon.app.infra.cache.manager;

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
     * 회원의 프로필 정보 저장
     * @param memberId 대상 회원번호
     * @param profile 회원 프로필 정보 객체
     */
    void saveProfile(Long memberId, Object profile);

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
     * @param sessionId 요청 세션 아이디
     * @param mailRequest 인증 메일 요청 DTO 객체
     */
    boolean recordVerifyMail(String sessionId, Object mailRequest);

    /**
     * 최근 검색어 목록 조회
     * @param memberId 조회 대상 회원번호
     * @return 회원이 최근에 검색한 검색어 목록 반환
     */
    List<String> getLatestSearchList(Long memberId);

    /**
     * 회원 프로필 조회
     * @param memberId 조회 대상 회원번호
     * @param type 저장했던 DTO Class Type
     * @return 조회된 회원 프로필 반환
     */
    <T> T getProfile(Long memberId, Class<T> type);

    /**
     * 이메일 인증 요청 조회
     * @param sessionId 요청 세션 아이디
     * @param type 저장했던 DTO Class Type
     */
    <T> T getMailRequest(String sessionId, Class<T> type);

    /**
     * 기존 캐시데이터를 불러오기
     * @param entityName 대상 엔티티명
     * @param id         구별 가능한 아이디 값 (sessionId, memberId, ...)
     * @param clazz 저장했던 DTO Class Type
     * @return 조회된 캐시데이터 (미존재 시 null)
     */
    <T> T getCache(String entityName, Object id, Class<T> clazz);


    /**
     * 기존 캐시데이터를 불러오거나 새로운 캐시데이터 생성 (특정 엔티티의 작성/수정에 대한 캐시 데이터 생성)
     * @param entityName 대상 엔티티명
     * @param id         구별 가능한 아이디 값 (sessionId, memberId, ...)
     * @param clazz 저장했던 DTO Class Type
     * @return 새롭게 생성하는 경우 null, 이미 존재하는 경우 조회된 캐시 데이터 반환
     */
    <T> T getOrRecordCache(String entityName, Object id, Class<T> clazz);


    /**
     * 캐시데이터 갱신
     * @param id  구별 가능한 아이디 값 (sessionId, memberId, ...)
     * @param entityName 저장 대상 엔티티명
     * @param cacheData 캐시 데이터
     */
    void updateCache(String entityName, Object id, Object cacheData);

    /**
     * 캐시 데이터 조회
     * @param entityName 대상 엔티티명
     * @param id  구별 가능한 아이디 값 (sessionId, memberId, ...)
     */
    void removeCache(String entityName, Object id);


    /**
     * 특정 엔티티 백업 데이터 일괄 조회
     * @param entityName 대상 엔티티 이름
     * @param clazz      저장했던 DTO Class Type
     * @return 조회된 백업 데이터 목록
     */
    <T> List<T> getAllBackupValue(String entityName, Class<T> clazz);


    /**
     * 백업 키 삭제
     * @param key 대상 key
     */
    void removeBackupKey(String key);

}
