package studyon.app.layer.domain.lecture_like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import studyon.app.layer.domain.lecture_like.LectureLikeDTO;
import studyon.app.layer.domain.lecture_like.service.LectureLikeService;

import java.util.*;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-28) : phj 최초 작성
 */

/**
 * 강의 서비스 인터페이스
 * @version 1.0
 * @author phj
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/lecture-like")
public class LectureLikeController {
    private final LectureLikeService lectureLikeService;

    // 좋아요 등록
    @PostMapping("/{lectureId}/add")
    public ResponseEntity<?> addLike(@RequestBody LectureLikeDTO.Write dto) {
        lectureLikeService.addLike(dto);
        Map<String, Object> result = new HashMap<>();
        result.put("liked", true);
        result.put("likeCount", lectureLikeService.countByLectureId(dto.getLectureId()));
        return ResponseEntity.ok(result);
    }

    // 삭제
    @DeleteMapping("/{lectureId}/remove")
    public ResponseEntity<?> removeLike(@RequestBody LectureLikeDTO.Delete dto) {
        lectureLikeService.removeLike(dto);
        Map<String, Object> result = new HashMap<>();
        result.put("liked", false);
        result.put("likeCount", lectureLikeService.countByLectureId(dto.getLectureId()));
        return ResponseEntity.ok(result);
    }

    // 상태 조회
    @GetMapping("/{lectureId}/status")
    public ResponseEntity<?> checkLike(
            @PathVariable Long lectureId,
            @RequestParam Long memberId) {

        boolean liked = lectureLikeService.isLiked(memberId, lectureId);
        long likeCount = lectureLikeService.countByLectureId(lectureId);

        Map<String, Object> result = new HashMap<>();
        result.put("liked", liked);
        result.put("likeCount", likeCount);
        return ResponseEntity.ok(result);
    }
}
