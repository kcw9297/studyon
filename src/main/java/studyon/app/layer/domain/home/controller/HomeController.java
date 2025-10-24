package studyon.app.layer.domain.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final LectureService lectureService;

    @GetMapping("index.html")
    public String index() {
        return "redirect:/";
    }

    /*
    @GetMapping
    public String home(Model model) {
        return ViewUtils.returnView(model, View.HOME, "home");
    }
    */

    // 혹시 몰라서 기존 코드 남겨놓음

    /**
     * [GET] 해당하는 과목 추천 페이지
     */
    @GetMapping
    public String viewHome(Model model) {
        /*
         [1] 최신순 강의 목록/인기(수강 학생 수) 목록 생성
        List<LectureDTO.Read> recentLecture = lectureService.readAllRecentLectures(count);
        List<LectureDTO.Read> popularLecture = lectureService.readAllPopularLectures(count);
         [2] 모델에 변수 바인딩
        model.addAttribute("recentLecture", recentLecture);
        model.addAttribute("popularLecture", popularLecture);
         [3] 뷰 리턴
        */

        // [1] 뷰 리턴
        return ViewUtils.returnView(model, View.HOME, "home");
    }
}
