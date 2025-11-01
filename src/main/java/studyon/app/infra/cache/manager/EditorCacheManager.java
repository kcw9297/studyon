package studyon.app.infra.cache.manager;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-26) : kcw97 최초 작성
 */

import java.util.List;

/**
 * 에디터 전용 케시 로직 처리
 * @version 1.0
 * @author kcw97
 */
public interface EditorCacheManager {


    /**
     * 캐시데이터 기록
     * @param id            구별 가능한 아이디 값
     * @param cacheData     캐시 데이터
     */
    void recordEditorCache(Object id, Object cacheData);


    /**
     * 캐시데이터 갱신
     * @param id            구별 가능한 아이디 값
     * @param cacheData     캐시 데이터
     */
    void updateEditorCache(Object id, Object cacheData);


    /**
     * 에디터 케시데이터 불러오기
     * @param id         구별 가능한 아이디 값
     * @param clazz      저장했던 DTO Class Type
     * @return 조회된 캐시데이터 (미존재 시 null)
     */
    <T> T getEditorCache(Object id, Class<T> clazz);


    /**
     * 캐시 데이터 조회 후, 아주 짧은 시간 후에 만료되도록 변경
     * @param id        구별 가능한 아이디 값
     * @param cacheData 새롭게 삽입되는 데이터
     */
    void setCacheWithShortExpired(Object id, Object cacheData);


    /**
     * 원본 캐시 key가 만료된 고아 상태의 백업 캐시데이터 일괄 조회
     * @return 조회된 백업 캐시데이터 리스트
     */
    <T> List<T> getAndRemoveAllOrphanCache(Class<T> clazz);
}
