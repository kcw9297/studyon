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
    void recordLatestSearch(Long memberId, String keyword);

    /**
     * 최근 검색어 목록 조회
     * @param memberId 조회 대상 회원번호
     * @return 회원이 최근에 검색한 검색어 목록 반환
     */
    List<String> getLatestSearchList(Long memberId);


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
    boolean removeAuthRequest(String token);


    /**
     * 기존 캐시데이터를 불러오기
     * @param entityName 대상 엔티티명
     * @param methodType 행동 유형
     * @param id         구별 가능한 아이디 값 (sessionId, memberId, ...)
     * @param clazz      저장했던 DTO Class Type
     * @return 조회된 캐시데이터 (미존재 시 null)
     */
    <T> T getCache(String entityName, String methodType, Object id, Class<T> clazz);


    /**
     * 기존 캐시데이터를 불러오기
     * @param entityName    대상 엔티티명
     * @param methodType    행동 유형
     * @param entityId      대상 엔티티 번호
     * @param id            구별 가능한 아이디 값 (sessionId, memberId, ...)
     * @param clazz         저장했던 DTO Class Type
     * @return 조회된 캐시데이터 (미존재 시 null)
     */
    <T> T getCache(String entityName, String methodType, Long entityId, Object id, Class<T> clazz);


    /**
     * 기존 캐시데이터를 불러오거나 새로운 캐시데이터 생성 (특정 엔티티의 작성/수정에 대한 캐시 데이터 생성)
     * @param entityName 대상 엔티티명
     * @param methodType 행동 유형
     * @param id         구별 가능한 아이디 값 (sessionId, memberId, ...)
     * @param clazz      저장했던 DTO Class Type
     * @return 새롭게 생성하는 경우 null, 이미 존재하는 경우 조회된 캐시 데이터 반환
     */
    <T> T getOrRecordCache(String entityName, String methodType, Object id, Class<T> clazz);


    /**
     * 기존 캐시데이터를 불러오기 전, 다른 엔티티에 대한 캐시를 삭제하고 조회
     * @param entityName    대상 엔티티명
     * @param methodType    행동 유형
     * @param entityId      대상 엔티티 번호
     * @param id            구별 가능한 아이디 값 (sessionId, memberId, ...)
     * @param clazz         저장했던 DTO Class Type
     * @return 조회된 캐시데이터 (미존재 시 null)
     */
    <T> T getCacheAndDeleteOldCache(String entityName, String methodType, Long entityId, Object id, Class<T> clazz);


    /**
     * 캐시데이터 갱신
     * @param entityName    저장 대상 엔티티명
     * @param actionType    행동 유형
     * @param id            구별 가능한 아이디 값 (sessionId, memberId, ...)
     * @param cacheData     캐시 데이터
     */
    void updateCache(String entityName, String actionType, Object id, Object cacheData);


    /**
     * 캐시데이터 갱신
     * @param entityName    저장 대상 엔티티명
     * @param actionType    행동 유형
     * @param entityId      대상 엔티티 번호
     * @param id            구별 가능한 아이디 값 (sessionId, memberId, ...)
     * @param cacheData     캐시 데이터
     */
    void updateCache(String entityName, String actionType, Long entityId, Object id, Object cacheData);


    /**
     * 캐시 데이터 삭제 (백업 데이터 삭제 x)
     * @param entityName 대상 엔티티명
     * @param actionType 행동 유형
     * @param id         구별 가능한 아이디 값 (sessionId, memberId, ...)
     */
    void removeCache(String entityName, String actionType, Object id);

    /**
     * 캐시 데이터 삭제 (백업 데이터 삭제 x)
     * @param entityName    대상 엔티티명
     * @param actionType    행동 유형
     * @param entityId      대상 엔티티 번호
     * @param id            구별 가능한 아이디 값 (sessionId, memberId, ...)
     */
    void removeCache(String entityName, String actionType, Long entityId, Object id);


    /**
     * 캐시 및 백업 데이터 삭제
     * @param entityName 대상 엔티티명
     * @param actionType 행동 유형
     * @param id         구별 가능한 아이디 값 (sessionId, memberId, ...)
     */
    void removeCacheAndBackup(String entityName, String actionType, Object id);


    /**
     * 캐시 및 백업 데이터 삭제
     * @param entityName    대상 엔티티명
     * @param actionType    행동 유형
     * @param entityId      대상 엔티티 번호
     * @param id            구별 가능한 아이디 값 (sessionId, memberId, ...)
     */
    void removeCacheAndBackup(String entityName, String actionType, Long entityId, Object id);


    /**
     * 특정 엔티티 백업 데이터 일괄 조회
     * @param entityName 대상 엔티티 이름
     * @param clazz      저장했던 DTO Class Type
     * @return 조회된 백업 데이터 목록
     */
    <T> List<T> getAndRemoveAllBackup(String entityName, Class<T> clazz);


}
