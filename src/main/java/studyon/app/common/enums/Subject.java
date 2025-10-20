package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 */

/**
 * 선생님 과목
 * @version 1.0
 * @author khj00
 */
@Getter
public enum Subject {
    KOREAN("국어"),
    MATH("수학"),
    ENGLISH("영어"),
    SCIENCE("과학탐구"),
    SOCIAL("사회탐구");

    private final String value;

    Subject(String value) {
        this.value = value;
    }

    public static List<Subject> get() {
        return Arrays.asList(Subject.values());
    }
}
