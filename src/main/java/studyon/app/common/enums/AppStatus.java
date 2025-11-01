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
    ACCESS_DENIED(403, "잘못된 요청입니다."), // 시큐리티 외 비즈니스적 상황으로 접근을 제한하는 경우


    /* 검증 및 인증 상태 */
    VALIDATION_INVALID_PARAMETER(400, "입력하신 값을 다시 확인해 주세요."),
    AUTH_MAIL_NOT_FOUND(400, "탈퇴 혹은 정지 상태거나 존재하지 않는 이메일입니다."),
    AUTH_INCORRECT_CODE(400, "인증 코드가 일치하지 않습니다."),
    AUTH_DUPLICATE_EMAIL(400, "이미 가입한 이메일이 존재합니다."),
    AUTH_REQUEST_EXPIRED(403, "인증 요청이 만료되었습니다. 다시 시도해 주세요."),
    AUTH_REQUEST_ALREADY_EXIST(403, "인증 요청이 이미 존재합니다. 잠시 후에 다시 시도해 주세요."),
    AUTH_INVALID_REQUEST(403, "이미 만료되었거나 유효하지 않은 요청입니다."),

    /* 유틸 클래스 상태 (아직 구제적으로 케이스를 나누진 않음) */
    UTILS_LOGIC_FAILED(500, "처리 중 오류가 발생했습니다. 잠시 후에 다시 시도해 주세요."),

    /* 에디터 상태 */
    EDITOR_CACHE_NOT_EXIST(400, "세션이 만료되었습니다. 다시 시도해 주세요"),

    /* 결제(Payment/PaymentManager) 상태 */
    PAYMENT_NOT_FOUND(500, "결제 정보가 존재하지 않습니다"),
    PAYMENT_INVALID_PAYMENT_UID(500, "결제에 실패했습니다. 존재하지 않는 결제 정보입니다."),
    PAYMENT_INVALID_AMOUNT(500, "결제에 실패했습니다. 실제 결제 금액과 일치하지 않습니다."),
    PAYMENT_LOGIC_FAILED(500, "결제에 실패했습니다. 잠시 후에 다시 시도해 주세요."),
    PAYMENT_ALREADY_REFUNDED(400, "이미 환불이 완료된 결제입니다."),
    PAYMENT_REFUND_NOT_AVAILABLE(400, "환불 가능 기간이 경과하였습니다.\n(결제일로부터 1년 이내에만 가능)"),
    PAYMENT_INVALID_REQUEST(403, "유효하지 않은 결제 요청입니다."),
    PAYMENT_ALREADY_PAYED(403, "이미 결제한 강의입니다."),

    /* Spring Security 처리 상태 */
    SECURITY_INCORRECT_USERNAME_PASSWORD(400, "이메일과 비밀번호가 일치하지 않습니다"),
    SECURITY_OATH2_AUTHENTICATION_FAILED(500, "소셜로그인 정보조회에 실패했습니다. 잠시 후에 시도해 주세요"),
    SECURITY_INACTIVATED(403, "현재 정지된 상태입니다. 관리자에게 문의하세요."),
    SECURITY_WITHDRAWAL(403, "이미 탈퇴처리된 회원입니다."),
    SECURITY_LOGIN_REQUIRED(401, "로그인이 필요한 서비스입니다. 로그인 하시겠습니까?"),
    SECURITY_ACCESS_DENIED(403, "잘못된 요청입니다."),

    /* 회원 상태 */
    MEMBER_NOT_FOUND(500, "이미 탈퇴했거나 존재하지 않는 회원 정보입니다"),
    MEMBER_DUPLICATE_NICKNAME(500, "이미 가입한 닉네임이 존재합니다."),
    MEMBER_DUPLICATE_EMAIL(500, "이미 가입한 이메일이 존재합니다."),
    MEMBER_OK_EDIT_NICKNAME(200, "닉네임을 변경했습니다."),
    MEMBER_OK_EDIT_PASSWORD(200, "비밀번호를 변경했습니다."),

    /* AUTH */
    AUTH_OK_EDIT_PASSWORD(200, "비밀번호를 변경했습니다."),

    /* 공지 상태 */
    NOTICE_NOT_FOUND(500, "이미 삭제되었거나 존재하지 않는 공지입니다."),
    NOTICE_NOT_EXIST_TITLE_AND_IMAGE(400, "공지사항 제목 혹은 이미지가 등록되지 않았습니다.\n이미지와 제목을 등록 후 다시 시도해 주세요."),
    NOTICE_OK_INITIALIZE(200, "공지사항을 초기화 했습니다."),

    /* 배너 상태 */
    BANNER_NOT_FOUND(500, "이미 삭제되었거나 존재하지 않는 배너입니다."),
    BANNER_NOT_EXIST_TITLE_AND_IMAGE(400, "배너 제목 혹은 이미지가 등록되지 않았습니다.\n이미지와 제목을 등록 후 다시 시도해 주세요."),
    BANNER_OK_INITIALIZE(200, "배너를 초기화 했습니다."),

    /* 선생님 상태 */
    TEACHER_NOT_FOUND(500, "선생님 정보가 존재하지 않습니다."),

    /* 파일(Entity) 상태 */
    FILE_NOT_FOUND(500, "파일이 존재하지 않습니다."),

    /* 강의(Lecture) 상태*/
    LECTURE_NOT_FOUND(500,"존재하지 않거나 판매중이 아닌 강의입니다."),
    LECTURE_NOT_ON_SALE(500,"현재 강의는 판매 중이 아닙니다.\n잠시 후에 다시 시도해 주세요."),
    LECTURE_THUMBNAIL_NOT_FOUND(500, "강의 썸네일이 존재하지 않습니다."),
    LECTURE_REJECT_NOW(400, "현재 강의는 반려된 상태입니다.\n검토 후 다시 승인요청을 해 주세요."),
    LECTURE_SALE_START_NOT_AVAILABLE(400, "승인되지 않은 강의는 판매할 수 없습니다."),
    LECTURE_STATE_NOT_EDITABLE(400, "해당 상태로 변경할 수 없습니다\n잘못된 상태변경 요청입니다."),
    LECTURE_OK_START_SALE(200, "판매 상태로 변경했습니다."),
    LECTURE_OK_STOP_SALE(200, "미판매 상태로 변경했습니다."),
    LECTURE_OK_PENDING(200, "강의 등록요청을 완료했습니다."),
    LECTURE_OK_REGISTER(200, "강의 등록처리를 완료했습니다."),
    LECTURE_OK_REJECT(200, "강의 반려처리를 완료했습니다."),
    LECTURE_OK_CREATE(200, "강의 생성을 완료했습니다"),

    /* QNA 상태*/
    QUESTION_NOT_FOUND(500, "질문이 존재하지 않습니다.");


    private final int httpCode;     // HTTP 코드
    private final String message;   // 전달 메세지

    AppStatus(int httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }
}
