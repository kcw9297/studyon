package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import studyon.app.layer.base.dto.Rest;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 일부 상수 "AppStatus" 상수로 통합
 */

/**
 * 공용으로 사용하는 메세지 상수 관리 클래스
 * @version 1.1
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Msg {

    // validator message
    public static final String VALIDATOR_LONG_RANGE = "%d-%d 사이 값 입력";
    public static final String VALIDATOR_DOUBLE_RANGE = "%.2f-%.2f 사이 값 입력";

}
