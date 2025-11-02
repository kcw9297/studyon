package studyon.app.layer.domain.lecture.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.*;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.validation.annotation.EditorContent;
import studyon.app.layer.base.validation.annotation.LongRange;
import studyon.app.layer.base.validation.annotation.Text;
import studyon.app.layer.base.validation.annotation.Title;
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
@Validated
public class TeacherLectureRestController {

    private final LectureService lectureService;
    private final EditorService editorService;

    /**
     * [GET] 선생님 강의 프로필 조회
     */


    /**
     * [POST] 강의 상세 소개 수정 시, 에디터 캐시데이터 생성 (캐시데이터)
     */
    @PostMapping("/cache")
    public ResponseEntity<?> createCache(){

        // [1] 임의의 아이디 생성 후 캐시 생성
        String id = StrUtils.createUUID();
        editorService.recordEditorCache(id);

        // [2] 서버에서 생성한 아이디 값 반환
        return RestUtils.ok(id);
    }


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

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);
        rq.setTeacherId(profile.getTeacherId()); // 선생님 번호 삽입

        // [2] 강의 생성
        Long lectureId = lectureService.create(rq);

        // [3] 에디터 임시 데이터 삭제
        return RestUtils.ok(AppStatus.LECTURE_OK_CREATE, "/teacher/management/lectureinfo/%d".formatted(lectureId));
    }

    /**
     * [PATCH] 강의 제목 갱신
     */
    @PatchMapping("/{lectureId}/title")
    public ResponseEntity<?> editTitle(@PathVariable Long lectureId,
                                       @Title(min = 4, max = 20) String title,
                                       HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 정보 수정
        lectureService.editTitle(lectureId, profile.getTeacherId(), title);

        // [3] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_EDIT_TITLE);
    }


    /**
     * [PATCH] 강의 소개 갱신
     */
    @PatchMapping("/{lectureId}/summary")
    public ResponseEntity<?> editSummary(@PathVariable Long lectureId,
                                         @Text(min = 10, max = 100) String summary,
                                         HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 정보 수정
        lectureService.editSummary(lectureId, profile.getTeacherId(), summary);

        // [3] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_EDIT_SUMMARY);
    }


    /**
     * [PATCH] 강의 상세 소개 갱신
     */
    @PatchMapping("/{lectureId}/description")
    public ResponseEntity<?> editDescription(@PathVariable Long lectureId,
                                             @EditorContent(min = 10, max = 2000) String description, String editorId,
                                             HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 정보 수정
        lectureService.editDescription(lectureId, profile.getTeacherId(), editorId, description);

        // [3] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_EDIT_DESCRIPTION);
    }


    /**
     * [PATCH] 강의 상세 소개 갱신
     */
    @PatchMapping("/{lectureId}/lecture-target")
    public ResponseEntity<?> editLectureTarget(@PathVariable Long lectureId,
                                               @NotNull(message = "강의 대상을 선택해야 합니다.") LectureTarget lectureTarget,
                                               HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 정보 수정
        lectureService.editLectureTarget(lectureId, profile.getTeacherId(), lectureTarget);

        // [3] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_EDIT_LECTURE_TARGET);
    }


    /**
     * [PATCH] 강의 상세 소개 갱신
     */
    @PatchMapping("/{lectureId}/subject-detail")
    public ResponseEntity<?> editLectureTarget(@PathVariable Long lectureId,
                                               @NotNull(message = "강의 세부 과목을 선택해야 합니다.") SubjectDetail subjectDetail,
                                               HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 정보 수정
        lectureService.editSubjectDetail(lectureId, profile.getTeacherId(), subjectDetail);

        // [3] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_EDIT_SUBJECT_DETAIL);
    }


    /**
     * [PATCH] 강의 상세 소개 갱신
     */
    @PatchMapping("/{lectureId}/difficulty")
    public ResponseEntity<?> editLectureTarget(@PathVariable Long lectureId,
                                               @NotNull(message = "강의 난이도를 선택해야 합니다.") Difficulty difficulty,
                                               HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 정보 수정
        lectureService.editDifficulty(lectureId, profile.getTeacherId(), difficulty);

        // [3] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_EDIT_DIFFICULTY);
    }

    /**
     * [PATCH] 강의 상세 소개 갱신
     */
    @PatchMapping("/{lectureId}/price")
    public ResponseEntity<?> editPrice(@PathVariable Long lectureId,
                                       @LongRange(min = 10000, max = 1000000) Long price,
                                       HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 정보 수정
        lectureService.editPrice(lectureId, profile.getTeacherId(), price);

        // [3] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_EDIT_PRICE);
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
