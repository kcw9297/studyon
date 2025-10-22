package studyon.app.layer.domain.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.log.Log;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-19) : kcw97 최초 작성
 */

/**
 * 로그 관련 정보 조회 레포지토리
 * @version 1.0
 * @author kcw97
 */

public interface LogRepository extends JpaRepository<Log, Long> {

}
