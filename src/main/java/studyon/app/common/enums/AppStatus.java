package studyon.app.common.enums;

import lombok.Getter;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-23) : kcw97 에러코드 구조 재작성
 *  ▶ ver 1.2 (2025-10-23) : kcw97 enum 형태로 변경
 */

/**
 * 웹 애플리케이션의 상태 값을 관리하는 클래스 (= 에러 코드)
 * @version 1.2
 * @author kcw97
 */

@Getter
public enum AppStatus {

    /* 범용 상태 */
    OK(200, "요청 처리를 완료했습니다."),
    SERVER_ERROR(500, "서버 오류가 발생했습니다. 잠시 후에 다시 시도해 주세요."),
    SESSION_EXPIRED(403, "세션이 만료되었습니다."),
    CACHE_EXPIRED(403, "정보가 만료되었습니다."),

    /* 검증 및 인증 상태 */
    VALIDATION_INVALID_PARAMETER(400, "입력하신 값을 다시 확인해 주세요"),
    AUTH_REQUEST_EXPIRED(400, "인증 요청이 만료되었습니다. 다시 시도해 주세요."),
    AUTH_INCORRECT_CODE(400, "인증 코드가 일치하지 않습니다."),
    AUTH_INVALID_REQUEST(500, "이미 만료되었거나 유효하지 않은 요청입니다."),

    /* 유틸 클래스 상태 (아직 구제적으로 케이스를 나누진 않음) */
    UTILS_LOGIC_FAILED(500, "처리 중 오류가 발생했습니다. 잠시 후에 다시 시도해 주세요."),

    /* 매니저 클래스 상태 */
    PAYMENT_INVALID_PAYMENT_UID(500, "결제에 실패했습니다. 존재하지 않는 결제 정보입니다."),
    PAYMENT_INVALID_AMOUNT(500, "결제에 실패했습니다. 실제 결제 금액과 일치하지 않습니다."),
    PAYMENT_ALREADY_REFUNDED(500, "이미 환불이 완료된 결제입니다."),
    PAYMENT_LOGIC_FAILED(500, "결제에 실패했습니다. 잠시 후에 다시 시도해 주세요."),

    /* Spring Security 처리 상태 */
    SECURITY_INCORRECT_USERNAME_PASSWORD(403, "아이디와 비밀번호가 일치하지 않습니다"),
    SECURITY_OATH2_AUTHENTICATION_FAILED(500, "소셜로그인 정보조회에 실패했습니다. 잠시 후에 시도해 주세요"),
    SECURITY_INACTIVATED(403, "현재 정지된 상태입니다. 관리자에게 문의하세요."),
    SECURITY_WITHDRAWAL(403, "이미 탈퇴처리된 회원입니다."),
    SECURITY_LOGIN_REQUIRED(401, "로그인이 필요한 서비스입니다. 로그인 하시겠습니까?"),
    SECURITY_INVALID_REQUEST(403, "잘못된 요청입니다."),

    /* 회원 상태 */
    MEMBER_NOT_FOUND(500, "이미 탈퇴했거나 존재하지 않는 회원 정보입니다"),
    MEMBER_DUPLICATE_NICKNAME(500, "이미 가입한 닉네임입니다."),
    MEMBER_DUPLICATE_EMAIL(500, "이미 가입한 이메일입니다."),


    /* 선생님 상태 */
    TEACHER_NOT_FOUND(500, "선생님 정보가 존재하지 않습니다."),

    /* 파일(Entity) 상태 */
    FILE_NOT_FOUND(500, "파일이 존재하지 않습니다.");

    private final int httpCode;     // HTTP 코드
    private final String message;   // 전달 메세지

    AppStatus(int httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }
}
