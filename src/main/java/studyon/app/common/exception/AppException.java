package studyon.app.common.exception;

import lombok.Getter;
import studyon.app.common.enums.AppStatus;

import java.util.HashMap;
import java.util.Map;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-24) : kcw97 최초 작성
 */

/**
 * 애플리케이션 전반에서 발생한 예외 처리 (커스텀 예외 중 최상위 예외)
 * <br>사용 코드 목록 : {@link studyon.app.common.enums.AppStatus}
 * @version 1.0
 * @author kcw97
 */

@Getter
public class AppException extends RuntimeException {

    private AppStatus appStatus; // 애플리케이션 상태 값 (AppStatus 참고)
    private Map<String, String> errors; // 유효성 검사에 실패한 값 목록 (닉네임, 이메일, 인증코드, ...)

    public AppException(AppStatus appStatus, Map<String, String> errors) {
        super();
        this.appStatus = appStatus;
        this.errors = errors;
    }

    public AppException(AppStatus appStatus) {
        super(appStatus.getMessage());
        this.appStatus = appStatus;
    }

    public AppException(AppStatus appStatus, Throwable cause) {
        super(appStatus.getMessage(), cause);
        this.appStatus = appStatus;
    }

    public AppException() {}
}
