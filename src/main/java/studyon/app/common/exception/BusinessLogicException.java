package studyon.app.common.exception;

import lombok.Getter;
import studyon.app.common.enums.AppStatus;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 최상위 커스텀 예외를 상속하는 것으로 변경
 */

/**
 * 비즈니스 로직 예외
 * @version 1.1
 * @author kcw97
 */

@Getter
public class BusinessLogicException extends AppException {

    public BusinessLogicException(AppStatus appStatus) {
        super(appStatus);
    }
}
