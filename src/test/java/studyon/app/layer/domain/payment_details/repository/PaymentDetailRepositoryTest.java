package studyon.app.layer.domain.payment_details.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment_details.PaymentDetails;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PaymentDetailRepositoryTest {
    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Test
    @DisplayName("강의 상세 내역 추가")
    void createPaymentDetails() {

        String fakeApiResponse = """
                {
                    "paymentId": 12345,
                    "status": "SUCCESS",
                    "transactionId": "TXN-00123",
                    "paidAmount": 8000,
                    "timestamp": "2025-10-16T10:00:00"
                }
            """;
    }


    @Test
    @DisplayName("강의 api 조회 내역 확인")
    void showApi() {

    }
}