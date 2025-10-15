package studyon.app.common.utils;

import org.springframework.core.env.Environment;

import java.util.Arrays;

public class AppUtils {

    public static boolean hasProfile(String profile, Environment env) {
        return Arrays.asList(env.getActiveProfiles()).contains(profile);
    }
}

