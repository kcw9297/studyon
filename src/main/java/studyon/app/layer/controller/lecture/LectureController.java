package studyon.app.layer.controller.lecture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.common.constant.URL;
import studyon.app.layer.base.utils.ViewUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LectureController {
    @GetMapping(URL.LECTURE)
    public String lectureRecommentView(Model model) {
        return ViewUtils.returnView(model, "layer/lecture/lecture_recomment");
    }
}
