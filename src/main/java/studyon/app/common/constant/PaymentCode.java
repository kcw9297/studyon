package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 주문 상태를 확인할 수 있는 코드
 * @version 1.0
 * @author kcw97
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentCode {

    public static final int OK = 0;
    public static final int ERROR_INTERNAL = -1; // 서버 오류 혹은 기타 예기치 않은 오류
    public static final int ERROR_AMOUNT_NOT_CORRESPOND = 1; // 결제액수 오류 (클라이언트와 불일치)
    public static final int ERROR_NOT_FOUND = 2; // 결제 정보를 찾지 못함 (잘못된 UID)
    public static final int ERROR_ALREADY_REFUNDED = 3; // 이미 환불이 완료된 결제
    public static final int ERROR_NOT_AVAILABLE = 4; // 기타 이유로 취소가 불가능한 결제 상태 (결제상태 확인 필요)
    public static final int ERROR_MISSING_PARAMETER = 5; // 필수 파라미터 누락 (입력 파라미터 확인)

}
