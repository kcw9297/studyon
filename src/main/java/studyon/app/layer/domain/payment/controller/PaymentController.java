package studyon.app.layer.domain.payment.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.constant.Param;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment.PaymentSession;
import studyon.app.layer.domain.payment.service.PaymentService;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(Url.PAYMENT)
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;


    @GetMapping("/write")
    public String showWriteView(Model model, HttpSession session,
                                @NotNull Long lectureId,
                                @NotBlank String token) {

        // [1] 회원 프로필 조회 후 검증
        Long memberId = SessionUtils.getMemberId(session);
        PaymentSession paymentRq = paymentService.verify(memberId, lectureId, token);

        model.addAttribute("data", paymentRq); // 결제 및 토큰정보 전달
        return ViewUtils.returnView(model, View.PAYMENT, "payment");
    }


    @GetMapping("/payment/complete")
    public String showPaymentCompleteView(Model model, HttpSession session,
                                          @NotNull Long paymentId) {

        // [1] 유효한 접근이 아닌경우 접근 불가 VIEW로 안내
        if (Objects.isNull(session.getAttribute(Param.VERIFIED)))
            return ViewUtils.return403(); // 결제 완료 외의 접근인 경우 불가 처리

        // [2] 프로필 조회 후, 결제정보 조회
        MemberProfile profile = SessionUtils.getProfile(session);
        PaymentDTO.Read data = paymentService.read(paymentId, profile);

        // [3] 인증 값 제거 후, 완료 페이지로 리다이렉트
        session.removeAttribute(Param.VERIFIED);
        model.addAttribute("data", data);
        return ViewUtils.returnView(model, View.PAYMENT, "payment-complete");
    }

    @GetMapping("/test")
    public String showTestView(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.PAYMENT, "test");
    }


}
