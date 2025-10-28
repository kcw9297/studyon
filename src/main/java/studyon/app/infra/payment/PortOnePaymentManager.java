package studyon.app.infra.payment;


import com.fasterxml.jackson.core.type.TypeReference;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import studyon.app.common.constant.Param;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.common.exception.ManagerException;
import studyon.app.common.utils.StrUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Iamport(PortOne) API 결제 서비스 처리
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class PortOnePaymentManager implements PaymentManager {

    private final IamportClient iamportClient;

    @Override
    public void checkPayment(String paymentApiResult) {

        try {
            // [1] 클라이언트 결제 결과 JSON 문자열 역직렬화
            Map<String, String> clientResult =
                    StrUtils.fromJson(paymentApiResult, new TypeReference<>() {});

            // [2] API 요청을 통해, 결제 결과 조회
            String impUid = clientResult.getOrDefault(Param.IMP_UID, "");
            IamportResponse<Payment> serverResult = iamportClient.paymentByImpUid(impUid);

            // [3] 상태코드 확인 (code 값이 0이 아니면 실패)
            // 상태 코드는 정상인 경우, 결제액수 검증 후 결과 코드 반환
            if (!Objects.equals(serverResult.getCode(), 0)) throwFailException(serverResult);
            else validatePaymentAmountAndReturnCode(serverResult, clientResult);


        } catch (BusinessLogicException e) {
            log.error(StrUtils.createLogStr(this.getClass(), "결제 처리 실패! 오류 : %s".formatted(e.getMessage())));
            throw e;
        }

        catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "예기치 않은 사유로 결제 처리 실패! 오류 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.SERVER_ERROR, e);
        }
    }


    @Override
    public void refundAll(String paymentUid, String refundReason) {

        try {

            // [1] 환불 요청 객체 생성
            CancelData cancelData = new CancelData(paymentUid, true);// 전액 대상 시, true (잔액 검증을 해줌)
            cancelData.setReason(refundReason);

            // [2] 환불 수행 후 결과 반환
            IamportResponse<Payment> serverResult = iamportClient.cancelPaymentByImpUid(cancelData);

            // [3] 상태코드 확인 (code 값이 0이 아니면 실패)
            // 상태 코드는 정상인 경우, 결제액수 검증 후 결과 코드 반환
            if (!Objects.equals(serverResult.getCode(), 0)) throwFailException(serverResult);


        } catch (BusinessLogicException e) {
            log.error(StrUtils.createLogStr(this.getClass(), "환불 처리 실패! 오류 : %s".formatted(e.getMessage())));
            throw e;
        }

        catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "예기치 않은 사유로 환불 처리 실패! 오류 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.SERVER_ERROR, e);
        }
    }


    // 처리 실패로 인한 예외 반환
    private void throwFailException(IamportResponse<Payment> serverResult) {

        /*
            Iamport 결제는 실패 시 응답 메세지가 정해져 있음 (코드는 실패 시 항상 -1)
            1) 잘못된 imp_uid : "존재하지 않는 결제건입니다"
            2) 중복 환불 시도 : "이미 취소된 결제입니다"
            3) 환불 불가 상태 : "취소할 수 없는 결제상태입니다"
            4) PG사 API 오류 : "PG사 통신 실패"
            5) 필수 파라미터 누락 : "필수 파라미터 누락"
         */


        // [1] 오류 메세지 분석
        String message = serverResult.getMessage();

        // [2] 메세지에 따라 비즈니스 로직 예외 던짐 (일부분만 처리)
        if (message.contains("존재하지 않는")) throw new BusinessLogicException(AppStatus.PAYMENT_INVALID_PAYMENT_UID);
        if (message.contains("이미 취소된")) throw new BusinessLogicException(AppStatus.PAYMENT_ALREADY_REFUNDED);
        throw new ManagerException(AppStatus.PAYMENT_LOGIC_FAILED);
    }

    // 결제액수 검증
    private void validatePaymentAmountAndReturnCode(IamportResponse<Payment> serverResult,
                                                   Map<String, String> clientResult) {

        // [1] 결제 검증 (클라이언트에서 조작이 있었는지 확인)
        BigDecimal serverAmount = serverResult.getResponse().getAmount();
        BigDecimal clientAmount = new BigDecimal(clientResult.getOrDefault(Param.PAID_AMOUNT, ""));
        log.warn(StrUtils.createLogStr(this.getClass(), "결제액수 확인. serverAmount = %.2f, clientAmount = %.2f".formatted(serverAmount, clientAmount)));

        // [2] 결제 액수 확인 후 검증. 실패 시 예외 던지기
        if (!Objects.equals(serverAmount, clientAmount))
            throw new BusinessLogicException(AppStatus.PAYMENT_INVALID_AMOUNT);
    }
}
