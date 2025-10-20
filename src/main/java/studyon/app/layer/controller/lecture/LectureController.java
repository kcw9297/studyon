package studyon.app.layer.controller.lecture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;

@Slf4j
@Controller
@RequestMapping(URL.LECTURE)
@RequiredArgsConstructor
public class LectureController {

    @GetMapping
    public String lectureRecommentView(Model model) {
        return ViewUtils.returnView(model, View.LECTURE,"lecture_recomment");
    }

}
