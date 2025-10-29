package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : khj00 최초 작성
 */


/**
 * Difficulty 유형
 * @version 1.0
 * @author khj00
 */

@Getter
public enum Difficulty {
    BASIC("기초"),
    STANDARD("핵심"),
    MASTER("응용"),
    ADVANCED("심화"),
    EXPERT("최상위");

    private final String value;

    Difficulty(String value) {this.value = value;}

    public static List<Difficulty> get() {
        return Arrays.asList(Difficulty.values());
    }
}
