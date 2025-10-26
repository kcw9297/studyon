package studyon.app.infra.cache.manager;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-26) : kcw97 최초 작성
 */

/**
 * 에디터 전용 케시 로직 처리
 * @version 1.0
 * @author kcw97
 */
public interface EditorCacheManager {

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
