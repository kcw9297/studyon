package studyon.app.common.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-29) : khs97 최초 작성
 */

/**
 * SUBJECT_DETAIL (2차 과목 분류)
 * 1차 Subject ENUM과 연동됨
 * @version 1.0
 * @author khs97
 */
@Getter
public enum SubjectDetail {

    // 수학
    MATH_I("수학 I", "MATH"),
    MATH_II("수학 II", "MATH"),
    PROBABILITY_STATISTICS("확률과 통계", "MATH"),

    // 영어
    ENGLISH_GRAMMAR("문법", "ENGLISH"),
    ENGLISH_READING("독해", "ENGLISH"),
    ENGLISH_LISTENING("듣기", "ENGLISH"),
    ENGLISH_CONVERSATION("회화", "ENGLISH"),

    // 국어
    KOREAN_LITERATURE("문학", "KOREAN"),
    KOREAN_NONLIT("비문학", "KOREAN"),
    KOREAN_GRAMMAR("문법", "KOREAN"),
    KOREAN_WRITING("작문", "KOREAN"),

    // 과학탐구
    SCIENCE_PHYSICS("물리 I", "SCIENCE"),
    SCIENCE_CHEMISTRY("화학 I", "SCIENCE"),
    SCIENCE_BIOLOGY("생명과학 I", "SCIENCE"),
    SCIENCE_EARTH("지구과학 I", "SCIENCE"),

    // 사회탐구
    SOCIAL_ETHICS("생활과 윤리", "SOCIAL"),
    SOCIAL_PHILOSOPHY("윤리와 사상", "SOCIAL"),
    SOCIAL_CULTURE("사회·문화", "SOCIAL"),
    SOCIAL_GEOGRAPHY("한국지리", "SOCIAL"),

    UNKNOWN("기타", "UNKNOWN");

    private final String name;       // 표시 이름 (한글)
    private final String parent;     // 1차 Subject 코드 (MATH, ENGLISH, 등)

    SubjectDetail(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    /** 전체 목록 반환 */
    public static List<SubjectDetail> get() {
        return Arrays.asList(SubjectDetail.values());
    }

    /** 1차 카테고리별 하위 목록 반환 */
    public static List<SubjectDetail> getByParent(String parentCode) {
        return Arrays.stream(SubjectDetail.values())
                .filter(sd -> sd.getParent().equalsIgnoreCase(parentCode))
                .toList();
    }
}
