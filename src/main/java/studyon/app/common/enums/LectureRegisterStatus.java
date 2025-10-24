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

    REGISTERED("등록완료"),
    PENDING("등록대기중"),
    UNREGISTERED("미등록"),
    REJECTED("반려"),
    IN_PROGRESS("등록중");

    private final String value;

    LectureRegisterStatus(String value) {
        this.value = value;
    }

    public static List<LectureRegisterStatus> get() {
        return Arrays.asList(LectureRegisterStatus.values());
    }
}
