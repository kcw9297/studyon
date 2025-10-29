package studyon.app.layer.domain.payment;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 결제 페이지 접근 전, 결제를 시도할 강의 정보를 저장하기 위한 세션
 * <br>이후 결제를 검증하기 위해 사용
 */

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentSession implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token; // 결제 검증 토큰

    private Long lectureId;

    private String thumbnailImagePath;

    private String lectureTitle;

    private String lectureTeacherNickname;

    private Long price;
}
