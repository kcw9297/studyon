package studyon.app.layer.base.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import studyon.app.common.constant.URL;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ViewUtils {

    public static String returnView(Model model, String viewPath) {
        model.addAttribute("body", viewResolve(viewPath));
        return "base/frame";
    }

    private static String viewResolve(String viewPath) {
        return "%s/%s.%s".formatted("/WEB-INF/views/", viewPath, "jsp");
    }
}
