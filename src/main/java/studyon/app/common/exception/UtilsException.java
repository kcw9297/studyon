package studyon.app.common.exception;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 최상위 커스텀 예외를 상속하게 변경
 */

import studyon.app.common.enums.AppStatus;

/**
 * Utils 클래스 내 체크 예외를 감싸는 예외
 * @version 1.1
 * @author kcw97
 */
public class UtilsException extends AppException {

    public UtilsException(AppStatus appStatus, Throwable cause) {
        super(appStatus, cause);

    }
}
