package studyon.app.common.exception;

import lombok.Getter;
import studyon.app.common.enums.AppStatus;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 최상위 커스텀 예외를 상속하는 것으로 변경
 *  ▶ ver 1.2 (2025-10-28) : kcw97 직접 오류상황을 전파하는 케이스(생성자) 추가
 */

/**
 * 비즈니스 로직 예외
 * @version 1.1
 * @author kcw97
 */

@Getter
public class BusinessLogicException extends AppException {

    // AppStatus 내 기본 메세지와 상태가 아닌, 직접 전달해야 하는 경우
    private String message;
    private int statusCode;

    public BusinessLogicException(AppStatus appStatus) {
        super(appStatus);
    }

    /**
     * 직접 비즈니스 예외 상황 전파
     * @param message 오류 메세지
     * @param statusCode 오류 코드
     */
    public BusinessLogicException(String message, int statusCode) {
        super();
        this.message = message;
        this.statusCode = statusCode;
    }
}
