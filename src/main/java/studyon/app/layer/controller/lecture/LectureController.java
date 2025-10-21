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

/*
 * [수정 이력]
 *  ▶ ver 1.1 (2025-10-20) : khj00 수정
 */

/**
 * 강의 서비스 연결 컨트롤러 클래스
 * @version 1.1
 * @author kcw97, khj00
 */


@Slf4j
@Controller
@RequestMapping(URL.LECTURE)
@RequiredArgsConstructor
public class LectureController {

    @GetMapping("/recomment")
    public String lectureRecommentView(Model model) {
        return ViewUtils.returnView(model, View.LECTURE,"lecture_recomment");
    }

}
