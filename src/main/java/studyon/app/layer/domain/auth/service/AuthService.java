package studyon.app.layer.domain.auth.service;

import studyon.app.layer.domain.auth.JoinCache;

/**
 * 인증 비즈니스 로직 처리
 * @version 1.0
 * @author kcw97
 */
public interface AuthService {

    void sendPasswordEditEmail(String email);

    void verify(String token);

    <T> T verifyAndGetData(String token, Class<T> dataType);

    JoinCache getJoinCache();

    void sendJoinEmail(String email);
}
