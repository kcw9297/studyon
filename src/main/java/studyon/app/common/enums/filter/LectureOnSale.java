package studyon.app.common.enums.filter;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-29) : kcw97 최초 작성
 */


/**
 * 강의 검색 판매 여부 필터
 * @version 1.0
 * @author khj00
 */

@Getter
public enum LectureOnSale {

    ON_SALE("판매중"),
    NOT_SALE("미판매");

    private final String value;

    LectureOnSale(String value) {this.value = value;}

    public static List<LectureOnSale> get() {
        return Arrays.asList(LectureOnSale.values());
    }
}
