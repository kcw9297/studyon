package studyon.app.common.enums.filter;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-29) : kcw97 최초 작성
 */


/**
 * 강의 검색어 필터 유형
 * @version 1.0
 * @author khj00
 */

@Getter
public enum LectureKeyword {

    TITLE("강의명"),
    CONTENT("내용"),
    TEACHER("선생님");

    private final String value;

    LectureKeyword(String value) {this.value = value;}

    public static List<LectureKeyword> get() {
        return Arrays.asList(LectureKeyword.values());
    }
}
