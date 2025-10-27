package studyon.app.layer.domain.auth.service;

import studyon.app.layer.domain.auth.JoinCache;

/**
 * 인증 비즈니스 로직 처리
 * @version 1.0
 * @author kcw97
 */
public interface AuthService {

    /**
     * 비밀번호 수정 URL을 제공하는 이메일 발송
     * @param email 수정 대상 이메일
     */
    void sendPasswordEditEmail(String email);

    /**
     * 인증 토큰 검증
     * @param token 대상 인증 토큰
     */
    void verify(String token);


    /**
     * 인증 토큰 검증 및 내부 데이터 조회
     * @param token 대상 인증 토큰
     * @param dataType 인증 데이터 클래스 타입
     * @return 인증 데이터
     */
    <T> T verifyAndGetData(String token, Class<T> dataType);

    /**
     * 인증 정보 삭제 (인증 성공 후 삭제 처리 용도)
     * @param token 대상 인증 토큰
     */
    void removeAuthRequest(String token);

    JoinCache getJoinCache();

    void sendJoinEmail(String email);
}
