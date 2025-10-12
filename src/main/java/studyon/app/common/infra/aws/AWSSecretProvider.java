package studyon.app.common.infra.aws;

import java.util.Map;

public interface AWSSecretProvider {

    Map<String, String> getSecret(String secretName);
}
