package studyon.app.layer.domain.lecture.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Entity;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.editor.service.EditorService;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.member.MemberProfile;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-22) : kcw97 생성
 */

/**
 * 선생님 페이지 내에서 조작하는 선생님 관련 REST API 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(Url.LECTURES_TEACHER_API)
@RequiredArgsConstructor
public class TeacherLectureRestController {

    private final LectureService lectureService;
    private final EditorService editorService;

    /**
     * [POST] 강의 에디터 이미지 업로드 (캐시데이터)
     */
    @PostMapping("/cache/description-image")
    public ResponseEntity<?> uploadDescriptionImage(String editorId, MultipartFile file){
        return RestUtils.ok(editorService.uploadEditorImage(editorId, Entity.LECTURE, file));
    }


    /**
     * [POST] 강의 등록
     */
    @PostMapping
    public ResponseEntity<?> create(@Validated LectureDTO.Create rq, HttpSession session){

        log.info("Create LectureDTO.Create rq={}", rq);

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);
        rq.setTeacherId(profile.getTeacherId()); // 선생님 번호 삽입

        // [2] 강의 생성
        Long lectureId = lectureService.create(rq);

        // [3] 에디터 임시 데이터 삭제
        return RestUtils.ok(AppStatus.LECTURE_OK_CREATE, "/teacher/management/lectureinfo/%d".formatted(lectureId));
    }


    /**
     * [PATCH] 강의 등록 신청 (등록대기 상태로 변경)
     */
    @PatchMapping("/{lectureId:[0-9]+}/pending")
    public ResponseEntity<?> pending(@PathVariable Long lectureId) {

        // [1] 상태 갱신 수행
        lectureService.pending(lectureId);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_PENDING);
    }



}
