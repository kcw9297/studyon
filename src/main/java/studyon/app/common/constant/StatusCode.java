package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-23) : kcw97 에러코드 구조 재작성
 */

/**
 * 웹 애플리케이션의 경우에 따른 상태 코드를 관리하는 클래스 (= 에러 코드)
 * @version 1.1
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusCode {

    /* 공용 코드 (0~10) */
    public static final int INTERNAL_ERROR = -1; // 기타 예기치 않은 서버 오류
    public static final int OK = 0;
    public static final int LOGIN_REQUIRED = 1;
    public static final int INVALID_REQUEST = 2;
    public static final int SESSION_EXPIRED = 3;
    public static final int CACHE_EXPIRED = 4;

    /* 인증/검증 코드 (10~19) */
    public static final int VALIDATION_INVALID_PARAMETER = 10; // 파라미터 유효성 검사에 실패한 경우
    public static final int VALIDATION_MAIL_EXPIRED = 11; // 만료된 메일
    public static final int VALIDATION_INCORRECT_MAIL_CODE = 12; // 일치하지 않는 메일 코드
    public static final int VALIDATION_MAIL_NOT_VALID = 13; // 메일 인증정보가 유효하지 않은 경우 (메일 인증 요청이 잘못된 경우)

    /* 파일 코드 (20~29) */
    public static final int FILE_NOT_FOUND = 20; // 파일 정보 미존재
    public static final int FILE_SIZE_RANGE_EXCEEDED = 21; // 파일 사이즈 범위 초과
    public static final int FILE_UPLOAD_FAILED = 22; // 파일 업로드 실패
    public static final int FILE_REMOVE_FAILED = 23; // 파일 삭제 실패
    public static final int FILE_DOWNLOAD_FAILED = 24; // 파일 삭제 실패

    /* 회원 코드 (30~39) */
    public static final int MEMBER_NOT_FOUND = 30;
    public static final int MEMBER_DUPLICATE_NICKNAME = 31;
    public static final int MEMBER_DUPLICATE_EMAIL = 32;

    /* 선생님 코드 (40~49) */
    public static final int TEACHER_NOT_FOUND = 40;


    /* 결제 코드 (50~59) */
    public static final int PAYMENT_NOT_FOUND = 50; // DB 내 결제정보가 없는 경우
    public static final int PAYMENT_INVALID_PAYMENT_UID = 51; // 결제 API 조회를 위한 UID값이 유효하지 않은 경우 (정보가 조회되지 않음)
    public static final int PAYMENT_INVALID_AMOUNT = 52; // 결제 액수가 실제 처리한 액수와 다른 경우 (조작된 경우)
    public static final int PAYMENT_ALREADY_REFUNDED = 53; // 이미 환불이 완료된 결제인 경우
    public static final int PAYMENT_PG_REQUEST_FAILED = 54; // PG사 통신에 실패한 경우
    public static final int PAYMENT_NOT_AVAILABLE = 55; // 이미 환불이 완료된 결제인 경우
    public static final int PAYMENT_MISSING_REQUIRED_PARAMETER = 56; // 기타 이유로 결제에 실패한 경우 (응답은 도착했으나, 위 경우와 다른 상황인 경우)
    public static final int PAYMENT_REQUEST_FAILED = 57; // 기타 이유로 결제에 실패한 경우 (응답은 도착했으나, 위 경우와 다른 상황인 경우)










}
