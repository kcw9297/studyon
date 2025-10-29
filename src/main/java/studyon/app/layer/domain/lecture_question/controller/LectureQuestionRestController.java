package studyon.app.layer.domain.lecture_question.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.lecture_question.LectureQuestionDTO;
import studyon.app.layer.domain.lecture_question.service.LectureQuestionService;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-29) : khs97 신규 생성
 */

/**
 * 강의 QnA(질문/답변) 비동기(REST API) 컨트롤러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(Url.LECTURE)
@RequiredArgsConstructor
public class LectureQuestionRestController {

    private final LectureQuestionService lectureQuestionService;

    /**
     * [POST] 질문 등록
     * - 세션 회원 ID 자동 주입
     * - FormData 또는 JSON Body로 lectureId, indexId, question 전달
     */
    @PostMapping("/question/register")
    public ResponseEntity<?> registerQuestion(LectureQuestionDTO.Write rq, HttpSession session) {
        // [1] 회원 ID 세션에서 주입
        Long memberId = SessionUtils.getMemberId(session);
        rq.setMemberId(memberId);

        // [2] 질문 등록 수행
        lectureQuestionService.register(rq);

        // [3] 성공 응답 반환
        return RestUtils.ok("질문이 성공적으로 등록되었습니다.");
    }


//    @GetMapping("/answer_and_question/{lectureId}/{indexId}")
//    public ResponseEntity<?> readAllByLectureAndIndex(
//            @PathVariable Long lectureId,
//            @PathVariable Long indexId) {
//
//        List<LectureQuestionDTO.Read> result = lectureQuestionService.readAllByLectureAndIndex(lectureId, indexId);
//        return RestUtils.ok(result);
//    }
//    /**
//     * [DELETE] 특정 질문 삭제 (본인만 가능)
//     */
//    @DeleteMapping("/{questionId}")
//    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId, HttpSession session) {
//        Long memberId = SessionUtils.getMemberId(session);
//        log.info("🗑️ [DELETE] 질문 삭제 요청 - questionId={}, memberId={}", questionId, memberId);
//
//        lectureQuestionService.delete(questionId, memberId);
//
//        return RestUtils.ok("질문이 삭제되었습니다.");
//    }
}
