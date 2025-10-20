package studyon.app.layer.controller.editor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.common.constant.URL;
import studyon.app.layer.base.utils.ViewUtils;

/**
 * Summernote 에디터 출력을 위한 컨트롤러 클래스
 * iframe 태그를 이용한 출력
 */

@Slf4j
@Controller
public class EditorController {

    @GetMapping(URL.EDITOR)
    public String editor(Model model) {
        return "base/editor";
    }
}
