package studyon.app.common.infra.aws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import studyon.app.common.constant.AppProfile;
import studyon.app.common.exception.common.ManagerException;

import java.util.Map;

@Profile(AppProfile.PROD)
@Component
@RequiredArgsConstructor
public class AWSSecretProviderImpl implements AWSSecretProvider {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, String> getSecret(String secretName) {

        try (SecretsManagerClient client = SecretsManagerClient.builder().region(Region.AP_NORTHEAST_2).build()) {

            // [1] AWS SecretManager 값 조회 요청
            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            // [2] 요청 응답
            GetSecretValueResponse response = client.getSecretValue(request);

            // [3] JSON 파싱 후 반환 (JSON String -> Map)
            return objectMapper.readValue(response.secretString(), new TypeReference<>() {});

        } catch (Exception e) {
            throw new ManagerException("AWS SecretsManager 값 조회 중 오류 발생!", e);
        }
    }
}
