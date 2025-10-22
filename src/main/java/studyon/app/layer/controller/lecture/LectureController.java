package studyon.app.layer.controller.lecture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Subject;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.1 (2025-10-20) : khj00 수정
 */

/**
 * 강의 서비스 연결 컨트롤러 클래스 (일단 추천 강의 페이지 중심)
 * @version 1.1
 * @author kcw97
 */


@Slf4j
@Controller
@RequestMapping(URL.LECTURE)
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    /**
     * [GET] 해당하는 과목 추천 페이지
     * @param subject 해당 과목
     * @param count 보여지는 개수 조정을 위한 카운트 변수
     */
    @GetMapping("/recommend/{subject}")
    public String lectureRecommendView(@PathVariable Subject subject, Model model, @RequestParam(defaultValue = "4") int count) {
        // [1] 모델에 변수 바인딩
        model.addAttribute("subject", subject);
        // [2] 뷰 리턴
        return ViewUtils.returnView(model, View.LECTURE,"lecture_recommend");
    }
}
