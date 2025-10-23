package studyon.app.layer.domain.lecture_video.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/player")
@RequiredArgsConstructor
public class LectureVideoViewController {

    /**
     * ✅ 영상 플레이어 페이지 (View 전용)
     * 예시: /player?lectureId=63&seq=1
     */
    @GetMapping
    public String playerPage(
            @RequestParam Long lectureId,
            @RequestParam(defaultValue = "1") int seq,
            Model model
    ) {
        log.info("🎬 [PLAYER PAGE] lectureId={}, seq={}", lectureId, seq);

        // 필요한 값만 전달 — JSP에서 fetch 쓸 수 있도록
        model.addAttribute("lectureId", lectureId);
        model.addAttribute("seq", seq);

        // 바로 JSP 렌더 (템플릿 X)
        return "page/lecture_video/lecture_player";
    }
}
