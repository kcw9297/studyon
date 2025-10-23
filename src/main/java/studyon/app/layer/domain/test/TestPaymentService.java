package studyon.app.layer.domain.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import studyon.app.infra.payment.PaymentManager;
import studyon.app.layer.base.exception.BusinessException;
import studyon.app.layer.domain.payment.PaymentDTO;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestPaymentService {

    private final PaymentManager paymentManager;

    /**
     * 결제 수행
     * @param rq 결제 결과 (주문요청 DTO)
     */
    public void pay(PaymentDTO.Pay rq) {

        // [1] 환불 수행
        int code = paymentManager.checkPayment(rq.getPaymentApiResult());
        if (!Objects.equals(code, 0)) throw new BusinessException("결제에 실패했습니다. 다시 시도해 주세요");

        // [2] 결제정보 저장 (추후 수행)
    }


    public void refund(String paymentUid) {
        int code = paymentManager.refundAll(paymentUid, "고객 요청");
        if (!Objects.equals(code, 0)) throw new BusinessException("환불에 실패했습니다. 다시 시도해 주세요");
    }
}
