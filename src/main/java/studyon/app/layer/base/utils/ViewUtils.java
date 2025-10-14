package studyon.app.layer.base.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ViewUtils {

    private static final String VIEW_PATH_LOGIN = "/login";

    public static String createLoginViewPath(String viewName) {
        return VIEW_PATH_LOGIN + "/" + viewName;
    }
}
