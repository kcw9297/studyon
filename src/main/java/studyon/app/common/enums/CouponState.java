package studyon.app.common.enums;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

/**
 * Coupon 유형
 * @version 1.0
 * @author kcw97
 */

public enum CouponState {
    ISSUED("생성됨"),
    USED("사용됨");

    private final String value;

    CouponState(String value) {this.value = value;}

    public static List<CouponState> get() {
        return Arrays.asList(CouponState.values());
    }
}
