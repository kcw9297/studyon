package studyon.app.common.enums.search;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-29) : kcw97 최초 작성
 */


/**
 * 강의 검색 정렬 필터
 * @version 1.0
 * @author khj00
 */

@Getter
public enum LectureSort {

    LATEST("최신순"),
    RATING("평점순"),
    POPULAR("수강생순");

    private final String value;

    LectureSort(String value) {this.value = value;}

    public static List<LectureSort> get() {
        return Arrays.asList(LectureSort.values());
    }
}
