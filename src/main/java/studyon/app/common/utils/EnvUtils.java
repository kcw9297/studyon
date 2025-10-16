package studyon.app.common.utils;

import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Objects;

public class EnvUtils {

    public static boolean hasProfile(Environment env, String profile) {
        return Arrays.asList(env.getActiveProfiles()).contains(profile);
    }

    public static boolean isPropertyEquals(Environment env, String property, String value) {

        // [1] 프로퍼티 조회
        String prop = env.getProperty(property);

        // [2] 프로퍼티 검증 후 반환
        return Objects.nonNull(prop) && Objects.equals(prop, value);
    }
}

