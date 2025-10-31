package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * SUBJECT_DETAIL (2차 과목 분류)
 * 수능 기준 1차 Subject ENUM과 연동됨
 * @version 1.2 (2025-10-29)
 * @author khs97
 */
@Getter
public enum SubjectDetail {

    // ✅ 국어
    KOREAN_COMMON("공통 국어", "KOREAN"),
    KOREAN_SPEECH_WRITING("화법과 작문", "KOREAN"),
    KOREAN_LANGUAGE_MEDIA("언어와 매체", "KOREAN"),
    KOREAN_LITERATURE("문학", "KOREAN"),
    KOREAN_READING("비문학(독서)", "KOREAN"),
    KOREAN_GRAMMAR("문법", "KOREAN"),

    // ✅ 수학
    MATH_I("수학 I", "MATH"),
    MATH_II("수학 II", "MATH"),
    GEOMETRY("기하", "MATH"),

    // ✅ 영어
    ENGLISH_READING("독해", "ENGLISH"),
    ENGLISH_LISTENING("듣기", "ENGLISH"),
    ENGLISH_GRAMMAR("문법", "ENGLISH"),
    ENGLISH_VOCAB("어휘", "ENGLISH"),
    ENGLISH_CONVERSATION("회화", "ENGLISH"),

    // ✅ 사회탐구
    SOCIAL_ETHICS_LIFE("생활과 윤리", "SOCIAL"),
    SOCIAL_ETHICS_THOUGHT("윤리와 사상", "SOCIAL"),
    SOCIAL_KOREAN_GEOGRAPHY("한국지리", "SOCIAL"),
    SOCIAL_WORLD_GEOGRAPHY("세계지리", "SOCIAL"),
    SOCIAL_EAST_ASIA_HISTORY("동아시아사", "SOCIAL"),
    SOCIAL_WORLD_HISTORY("세계사", "SOCIAL"),
    SOCIAL_ECONOMY("경제", "SOCIAL"),
    SOCIAL_POLITICS_LAW("정치와 법", "SOCIAL"),
    SOCIAL_CULTURE("사회·문화", "SOCIAL"),

    // ✅ 과학탐구
    SCIENCE_PHYSICS_I("물리 I", "SCIENCE"),
    SCIENCE_CHEMISTRY_I("화학 I", "SCIENCE"),
    SCIENCE_BIOLOGY_I("생명과학 I", "SCIENCE"),
    SCIENCE_EARTH_I("지구과학 I", "SCIENCE"),
    SCIENCE_PHYSICS_II("물리 II", "SCIENCE"),
    SCIENCE_CHEMISTRY_II("화학 II", "SCIENCE"),
    SCIENCE_BIOLOGY_II("생명과학 II", "SCIENCE"),
    SCIENCE_EARTH_II("지구과학 II", "SCIENCE");


    private final String name;    // 표시 이름 (한글)
    private final String parent;  // 1차 과목 ENUM 코드 (MATH, ENGLISH 등)

    SubjectDetail(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    /** 전체 목록 반환 */
    public static List<SubjectDetail> get() {
        return Arrays.asList(values());
    }

    /** 1차 과목별 하위 세부과목 목록 반환 */
    public static List<SubjectDetail> getByParent(String parentCode) {
        return Arrays.stream(values())
                .filter(sd -> sd.getParent().equalsIgnoreCase(parentCode))
                .toList();
    }
}
