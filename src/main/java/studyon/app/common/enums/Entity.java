package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * ENTITY (= Table) 유형
 * @version 1.0
 * @author kcw97
 */
@Getter
public enum Entity {
    MEMBER("member"),
    TEACHER("teacher"),
    LECTURE("lecture"),
    LECTURE_QUESTION("lecture_question"),
    LECTURE_ANSWER("lecture_answer"),
    PURCHASE("purchase"),
    REFUND("refund"),
    UNKNOWN("unknown");

    private final String value;

    Entity(String value) {this.value = value;}

    public static List<Entity> get() {
        return Arrays.asList(Entity.values());
    }
}
