package studyon.app.common.enums;

import lombok.Getter;
import java.util.Arrays;
import java.util.List;

/**
 * 강의 등록 상태
 * @version 1.0
 * @author kcw97
 */
@Getter
public enum LectureRegisterStatus {

    UNREGISTERED("미등록"),
    PENDING("등록대기중"),
    REGISTERED("등록완료"),
    REJECTED("반려");

    private final String value;

    LectureRegisterStatus(String value) {
        this.value = value;
    }

    public static List<LectureRegisterStatus> get() {
        return Arrays.asList(LectureRegisterStatus.values());
    }
}
