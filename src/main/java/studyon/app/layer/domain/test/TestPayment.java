package studyon.app.layer.domain.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.payment.PaymentDTO;

@Slf4j
@Controller
@RequestMapping("/testpayment")
@RequiredArgsConstructor
public class TestPayment {

    private final TestPaymentService testPaymentService;

    @Value("${payment.portone.id-code}")
    private String idCode; // 가맹점 식별코드


    @GetMapping
    public String testPayment(Model model) {
        model.addAttribute("idCode", idCode);
        return "page/test/payment";
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<?> pay(PaymentDTO.Pay rq) {

        log.warn("rq = {}", rq);
        testPaymentService.pay(rq);
        return RestUtils.ok(Rest.Message.of("주문 성공!!"));
    }

    @ResponseBody
    @PostMapping("/{paymentUid}/refund")
    public ResponseEntity<?> refund(@PathVariable String paymentUid) {
        testPaymentService.refund(paymentUid);
        return RestUtils.ok(Rest.Message.of("환불 성공!!"));
    }

}
