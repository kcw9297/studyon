package studyon.app.layer.base.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {

    public static String createSessionKey(String namespace, String sessionId) {
        return "%s:sessions:%s".formatted(namespace, sessionId);
    }

}
