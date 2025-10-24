package studyon.app.common.exception;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

import studyon.app.common.enums.AppStatus;

/**
 * Manager 클래스 내 체크 예외를 감싸는 예외
 * @version 1.0
 * @author kcw97
 */
public class ManagerException extends AppException {

    public ManagerException(AppStatus appStatus) {
        super(appStatus);
    }

    public ManagerException(AppStatus appStatus, Throwable cause) {
        super(appStatus, cause);
    }
}
