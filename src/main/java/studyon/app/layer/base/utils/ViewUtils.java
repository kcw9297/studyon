package studyon.app.layer.base.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import studyon.app.common.enums.View;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ViewUtils {

    public static String returnView(Model model, View view, String viewName) {
        model.addAttribute("body", resolvePageView(view, viewName));
        return "base/frame";
    }

    private static String resolvePageView(View view, String viewName) {
        return "%s/%s/%s.%s".formatted("/WEB-INF/views/page", view.getValue(), viewName, "jsp");
    }
}
