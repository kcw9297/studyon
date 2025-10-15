package studyon.app.layer.domain.payment;

import lombok.*;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.MemberDTO;

import java.time.LocalDateTime;

/**
 * 결제 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor
public class PaymentDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long paymentId;
        private LocalDateTime paidAt;
        private Double originalPrice;
        private Double paidPrice;
        private Double discountPrice;
        private Long memberId;
        private Long lectureId;
    }
}
