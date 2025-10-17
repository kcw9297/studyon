package studyon.app.layer.domain.log.service;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

import studyon.app.layer.domain.log.LogDTO;

/**
 * 로그 관련 핵심 로직 처리
 * @version 1.0
 * @author kcw97
 */
public interface LogService {

    /**
     * 로그 생성 (독립적 트랜잭션에서 동작)
     * @param request 로그 생성 요청
     */
    void generate(LogDTO.Generate request);
}
