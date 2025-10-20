package studyon.app.layer.base.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.util.HtmlUtils;
import studyon.app.common.enums.View;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ViewUtils {


    public static String returnView(Model model, View view, String viewName) {
        model.addAttribute("body", resolvePageView(view, viewName));
        return "base/frame";
    }

    private static String resolvePageView(View view, String viewName) {
        return "/WEB-INF/views/page/%s/%s.jsp".formatted(view.getValue(), viewName);
    }
}
