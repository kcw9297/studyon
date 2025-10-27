package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 강의 대상 Enum
 * @version 1.1
 * @author khs97
 */
@Getter
public enum LectureTarget {

    HIGH1("고1"),
    HIGH2("고2"),
    HIGH3("고3");

    private final String value;

    LectureTarget(String value) {
        this.value = value;
    }

    public static List<LectureTarget> get() {
        return Arrays.asList(LectureTarget.values());
    }
}
