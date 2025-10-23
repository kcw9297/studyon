package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-20) : kcw97 TEMP 유형 추가
 */
/**
 * ENTITY (= Table) 유형
 * @version 1.1
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
    PAYMENT("payment"),
    TEMP("temp"),
    UNKNOWN("unknown");

    private final String name;

    Entity(String name) {this.name = name;}

    public static List<Entity> get() {
        return Arrays.asList(Entity.values());
    }
}
