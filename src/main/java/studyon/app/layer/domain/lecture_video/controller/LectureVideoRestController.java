package studyon.app.layer.domain.lecture_video.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.lecture_video.LectureVideoDTO;
import studyon.app.layer.domain.lecture_video.service.LectureVideoService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lecture/video")
@RequiredArgsConstructor
public class LectureVideoRestController {

    private final LectureVideoService lectureVideoService;

    /**
     * ✅ 로그인한 사용자가 수강 중인 강의의 영상 목록 조회
     * 예시: GET /api/lecture/video/list?lectureId=63
     */
    @GetMapping("/list")
    public ResponseEntity<List<LectureVideoDTO.Read>> getLectureVideos(
            @RequestParam Long lectureId,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 영상 목록 요청 - lectureId={}", lectureId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("🎬 [VIDEO LIST] 요청 - memberId={}, lectureId={}", memberId, lectureId);

        List<LectureVideoDTO.Read> videos = lectureVideoService.getVideosForMemberLecture(memberId, lectureId);

        if (videos.isEmpty()) {
            log.warn("⚠️ 조회된 영상 없음 - memberId={}, lectureId={}", memberId, lectureId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(videos);
    }
}
