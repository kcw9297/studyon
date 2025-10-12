package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 행동 유형
 * @version 1.0
 * @author kcw97
 */
@Getter
public enum Action {

    WRITE("작성"),
    EDIT("갱신"),
    READ("읽기"),
    REMOVE("삭제"),
    INACTIVATE("비활성화"),
    JOIN("회원가입"),
    WITHDRAWAL("탈퇴"),
    PAY("결제"),
    REFUND("환불"),
    COUPON_ISSUE("쿠폰발급"),
    COUPON_USE("쿠폰사용"),
    UNKNOWN("불명");

    private final String value;

    Action(String value) {this.value = value;}

    public static List<Action> get() {
        return Arrays.asList(Action.values());
    }
}
