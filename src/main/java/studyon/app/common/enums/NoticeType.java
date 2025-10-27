package studyon.app.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 공지 유형
 * @version 1.0
 * @author kcw97
 */
@Getter
public enum NoticeType {

    NORMAL("일반"),
    EVENT("이벤트"),
    SYSTEM("시스템");

    private final String value;

    NoticeType(String value) {this.value = value;}

    @JsonValue
    public String getValue() {
        return value;
    }

    public static List<NoticeType> get() {
        return Arrays.asList(NoticeType.values());
    }
}
