package studyon.app.config;

import com.siot.IamportRestClient.IamportClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 결제 API 등록을 위한 설정 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Configuration
public class PaymentConfig {

    @Value("${payment.portone.api-key}")
    private String apiKey;

    @Value("${payment.portone.api-secret}")
    private String apiSecret;

    @Bean // Iamport(portone) 결제 API Client
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, apiSecret);
    }
}
