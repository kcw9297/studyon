package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Cache 유형
 * @version 1.0
 * @author kcw97
 */
@Getter
public enum Cache {

    MEMBER_PROFILE("member:profile"),
    MEMBER_LATEST_SEARCH("member:search:latest"),
    MEMBER_LOGIN("member:login"),
    CURRENT_LOGIN("current:login"),
    AUTH("auth"),
    PAYMENT("payment");

    private final String baseKey;

    Cache(String baseKey) {this.baseKey = baseKey;}

    public static List<Cache> get() {
        return Arrays.asList(Cache.values());
    }
}
