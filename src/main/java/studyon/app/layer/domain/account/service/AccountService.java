package studyon.app.layer.domain.account.service;

import studyon.app.layer.domain.account.JoinCache;

/**
 * 사용자 로그인, 회원가입과 관련한 핵심 로직 처리
 * @version 1.0
 * @author kcw97
 */

public interface AccountService {

    JoinCache getJoinCache();

    void invalidateJoinCache();
}
