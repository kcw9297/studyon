package studyon.app.layer.domain.auth.service;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-26) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-27) : kcw97 회원기입 인증 메일 전송 추가
 */

/**
 * 인증 비즈니스 로직 처리
 * @version 1.1
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

    /**
     * 회원가입 이메일 발송
     * @param email       가입 대상 이메일
     * @param joinRequest 가입 요청 데이터 (DTO)
     */
    void sendJoinEmail(String email, Object joinRequest);
}
